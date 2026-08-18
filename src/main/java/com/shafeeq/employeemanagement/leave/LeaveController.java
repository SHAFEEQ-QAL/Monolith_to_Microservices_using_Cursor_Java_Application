package com.shafeeq.employeemanagement.leave;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/leaves")
public class LeaveController {
    private final LeaveService service;
    public LeaveController(LeaveService service) { this.service = service; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public LeaveRequest create(@Valid @RequestBody LeaveRequestCreate request) { return service.create(request); }
    @GetMapping public List<LeaveRequest> all(@RequestParam(required = false) Long employeeId) { return service.findAll(employeeId); }
    @PatchMapping("/{id}/decision") public LeaveRequest decide(@PathVariable Long id, @RequestParam LeaveStatus status) { return service.decide(id, status); }
}
