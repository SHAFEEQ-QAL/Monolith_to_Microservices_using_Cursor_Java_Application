package com.shafeeq.employeemanagement.employee;

import com.shafeeq.employeemanagement.common.ResourceNotFoundException;
import com.shafeeq.employeemanagement.department.Department;
import com.shafeeq.employeemanagement.department.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository repository;
    private final DepartmentService departmentService;
    public EmployeeService(EmployeeRepository repository, DepartmentService departmentService) { this.repository = repository; this.departmentService = departmentService; }
    public Employee create(EmployeeRequest r) { Employee e = new Employee(); apply(e, r); e.setStatus(EmploymentStatus.ACTIVE); return repository.save(e); }
    @Transactional(readOnly = true) public List<Employee> findAll(Long departmentId) { return departmentId == null ? repository.findAll() : repository.findByDepartmentId(departmentId); }
    @Transactional(readOnly = true) public Employee findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee " + id + " not found")); }
    public Employee update(Long id, EmployeeRequest r) { Employee e = findById(id); apply(e, r); return repository.save(e); }
    public Employee changeStatus(Long id, EmploymentStatus status) { Employee e = findById(id); e.setStatus(status); return repository.save(e); }
    public Employee transfer(Long id, Long departmentId) { Employee e = findById(id); e.setDepartment(departmentService.findById(departmentId)); return repository.save(e); }
    private void apply(Employee e, EmployeeRequest r) { Department d = departmentService.findById(r.departmentId()); e.setFirstName(r.firstName()); e.setLastName(r.lastName()); e.setEmail(r.email()); e.setAnnualSalary(r.annualSalary()); e.setDepartment(d); }
}
