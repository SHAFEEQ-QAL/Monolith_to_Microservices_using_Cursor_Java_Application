package com.shafeeq.employeemanagement.notification;

import com.shafeeq.employeemanagement.common.ResourceNotFoundException;
import com.shafeeq.employeemanagement.employee.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;

@Service @Transactional
public class NotificationService {
    private final NotificationRepository repository; private final EmployeeService employeeService;
    public NotificationService(NotificationRepository repository, EmployeeService employeeService) { this.repository = repository; this.employeeService = employeeService; }
    public Notification create(NotificationRequest r) { Notification n = new Notification(); n.setEmployee(employeeService.findById(r.employeeId())); n.setSubject(r.subject()); n.setMessage(r.message()); n.setStatus(NotificationStatus.PENDING); n.setCreatedAt(Instant.now()); return repository.save(n); }
    @Transactional(readOnly = true) public List<Notification> findAll(Long employeeId) { return employeeId == null ? repository.findAll() : repository.findByEmployeeId(employeeId); }
    public Notification markSent(Long id) { Notification n = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notification " + id + " not found")); n.setStatus(NotificationStatus.SENT); n.setSentAt(Instant.now()); return repository.save(n); }
}
