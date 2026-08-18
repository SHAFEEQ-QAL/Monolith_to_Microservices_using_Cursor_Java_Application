package com.shafeeq.employeemanagement.payroll;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/payroll")
public class PayrollController {
    private final PayrollService service;
    public PayrollController(PayrollService service) { this.service = service; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Payroll create(@Valid @RequestBody PayrollRequest request) { return service.create(request); }
    @GetMapping public List<Payroll> all(@RequestParam(required = false) Long employeeId) { return service.findAll(employeeId); }
    @GetMapping("/{id}") public Payroll one(@PathVariable Long id) { return service.findById(id); }
}
