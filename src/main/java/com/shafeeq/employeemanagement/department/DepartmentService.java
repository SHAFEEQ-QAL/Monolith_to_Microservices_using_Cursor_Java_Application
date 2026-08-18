package com.shafeeq.employeemanagement.department;

import com.shafeeq.employeemanagement.common.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentService {
    private final DepartmentRepository repository;
    public DepartmentService(DepartmentRepository repository) { this.repository = repository; }
    public Department create(DepartmentRequest request) { return repository.save(new Department(request.name(), request.location())); }
    @Transactional(readOnly = true) public List<Department> findAll() { return repository.findAll(); }
    @Transactional(readOnly = true) public Department findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department " + id + " not found")); }
    public Department update(Long id, DepartmentRequest request) { Department d = findById(id); d.setName(request.name()); d.setLocation(request.location()); return repository.save(d); }
    public void delete(Long id) { repository.delete(findById(id)); }
}
