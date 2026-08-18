package com.shafeeq.employeemanagement.payroll;

import com.shafeeq.employeemanagement.employee.Employee;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payroll_records")
public class Payroll {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "employee_id") private Employee employee;
    @Column(nullable = false) private LocalDate payDate;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal grossAmount;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal deductions;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal netAmount;
    public Payroll() {}
    public Long getId() { return id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public LocalDate getPayDate() { return payDate; }
    public void setPayDate(LocalDate payDate) { this.payDate = payDate; }
    public BigDecimal getGrossAmount() { return grossAmount; }
    public void setGrossAmount(BigDecimal grossAmount) { this.grossAmount = grossAmount; }
    public BigDecimal getDeductions() { return deductions; }
    public void setDeductions(BigDecimal deductions) { this.deductions = deductions; }
    public BigDecimal getNetAmount() { return netAmount; }
    public void setNetAmount(BigDecimal netAmount) { this.netAmount = netAmount; }
}
