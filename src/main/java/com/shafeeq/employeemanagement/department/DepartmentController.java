package com.shafeeq.employeemanagement.department;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentClient client;
    public DepartmentController(DepartmentClient client) { this.client = client; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Department create(@Valid @RequestBody DepartmentRequest request) { return client.create(request); }
    @GetMapping public List<Department> all() { return client.findAll(); }
    @GetMapping("/{id}") public Department one(@PathVariable Long id) { return client.findById(id); }
    @PutMapping("/{id}") public Department update(@PathVariable Long id, @Valid @RequestBody DepartmentRequest request) { return client.update(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { client.delete(id); }
}
