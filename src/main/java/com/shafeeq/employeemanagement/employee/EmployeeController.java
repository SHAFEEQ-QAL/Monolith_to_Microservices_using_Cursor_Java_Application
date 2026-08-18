package com.shafeeq.employeemanagement.employee;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service) { this.service = service; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Employee create(@Valid @RequestBody EmployeeRequest request) { return service.create(request); }
    @GetMapping public List<Employee> all(@RequestParam(required = false) Long departmentId) { return service.findAll(departmentId); }
    @GetMapping("/{id}") public Employee one(@PathVariable Long id) { return service.findById(id); }
    @PutMapping("/{id}") public Employee update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest request) { return service.update(id, request); }
    @PatchMapping("/{id}/status") public Employee status(@PathVariable Long id, @RequestParam EmploymentStatus value) { return service.changeStatus(id, value); }
    @PatchMapping("/{id}/transfer") public Employee transfer(@PathVariable Long id, @RequestParam Long departmentId) { return service.transfer(id, departmentId); }
}
