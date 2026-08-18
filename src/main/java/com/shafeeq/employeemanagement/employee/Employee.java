package com.shafeeq.employeemanagement.employee;

import com.shafeeq.employeemanagement.department.Department;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "employees", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 100) private String firstName;
    @Column(nullable = false, length = 100) private String lastName;
    @Column(nullable = false, length = 150) private String email;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal annualSalary;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private EmploymentStatus status;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "department_id") private Department department;

    public Employee() {}
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public BigDecimal getAnnualSalary() { return annualSalary; }
    public void setAnnualSalary(BigDecimal annualSalary) { this.annualSalary = annualSalary; }
    public EmploymentStatus getStatus() { return status; }
    public void setStatus(EmploymentStatus status) { this.status = status; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
}
