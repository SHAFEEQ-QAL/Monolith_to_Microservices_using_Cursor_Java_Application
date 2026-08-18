package com.shafeeq.employeemanagement.employee;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record EmployeeRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email @NotBlank String email,
        @NotNull @Positive BigDecimal annualSalary,
        @NotNull Long departmentId) {}
