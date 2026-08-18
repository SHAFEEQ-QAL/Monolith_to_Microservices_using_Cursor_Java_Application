package com.shafeeq.employeemanagement.payroll;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PayrollRequest(@NotNull Long employeeId, @NotNull LocalDate payDate,
                             @NotNull @Positive BigDecimal grossAmount,
                             @NotNull @PositiveOrZero BigDecimal deductions) {}
