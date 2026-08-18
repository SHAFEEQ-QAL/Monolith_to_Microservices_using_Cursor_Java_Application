package com.shafeeq.employeemanagement.department;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService service;
    public DepartmentController(DepartmentService service) { this.service = service; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Department create(@Valid @RequestBody DepartmentRequest request) { return service.create(request); }
    @GetMapping public List<Department> all() { return service.findAll(); }
    @GetMapping("/{id}") public Department one(@PathVariable Long id) { return service.findById(id); }
    @PutMapping("/{id}") public Department update(@PathVariable Long id, @Valid @RequestBody DepartmentRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
