package com.shafeeq.employeemanagement.notification;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService service;
    public NotificationController(NotificationService service) { this.service = service; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Notification create(@Valid @RequestBody NotificationRequest request) { return service.create(request); }
    @GetMapping public List<Notification> all(@RequestParam(required = false) Long employeeId) { return service.findAll(employeeId); }
    @PatchMapping("/{id}/send") public Notification markSent(@PathVariable Long id) { return service.markSent(id); }
}
