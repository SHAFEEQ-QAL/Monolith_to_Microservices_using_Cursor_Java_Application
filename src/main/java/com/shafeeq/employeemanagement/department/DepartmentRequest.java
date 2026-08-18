package com.shafeeq.employeemanagement.department;

import jakarta.validation.constraints.NotBlank;

public record DepartmentRequest(@NotBlank String name, @NotBlank String location) {}
