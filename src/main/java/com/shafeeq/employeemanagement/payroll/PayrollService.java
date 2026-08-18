package com.shafeeq.employeemanagement.payroll;

import com.shafeeq.employeemanagement.common.ResourceNotFoundException;
import com.shafeeq.employeemanagement.employee.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @Transactional
public class PayrollService {
    private final PayrollRepository repository; private final EmployeeService employeeService;
    public PayrollService(PayrollRepository repository, EmployeeService employeeService) { this.repository = repository; this.employeeService = employeeService; }
    public Payroll create(PayrollRequest r) { if (r.deductions().compareTo(r.grossAmount()) > 0) throw new IllegalArgumentException("Deductions cannot exceed gross amount"); Payroll p = new Payroll(); p.setEmployee(employeeService.findById(r.employeeId())); p.setPayDate(r.payDate()); p.setGrossAmount(r.grossAmount()); p.setDeductions(r.deductions()); p.setNetAmount(r.grossAmount().subtract(r.deductions())); return repository.save(p); }
    @Transactional(readOnly = true) public List<Payroll> findAll(Long employeeId) { return employeeId == null ? repository.findAll() : repository.findByEmployeeId(employeeId); }
    @Transactional(readOnly = true) public Payroll findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payroll record " + id + " not found")); }
}
