package com.shafeeq.employeemanagement.notification;

import jakarta.validation.constraints.*;
public record NotificationRequest(@NotNull Long employeeId, @NotBlank @Size(max = 200) String subject,
                                  @NotBlank @Size(max = 1000) String message) {}
