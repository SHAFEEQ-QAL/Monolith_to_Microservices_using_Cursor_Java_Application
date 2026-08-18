package com.shafeeq.employeemanagement.notification;

import com.shafeeq.employeemanagement.employee.Employee;
import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name = "notifications")
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "employee_id") private Employee employee;
    @Column(nullable = false, length = 200) private String subject;
    @Column(nullable = false, length = 1000) private String message;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private NotificationStatus status;
    @Column(nullable = false) private Instant createdAt;
    private Instant sentAt;
    public Notification() {}
    public Long getId() { return id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public NotificationStatus getStatus() { return status; }
    public void setStatus(NotificationStatus status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getSentAt() { return sentAt; }
    public void setSentAt(Instant sentAt) { this.sentAt = sentAt; }
}
