package com.emmanuel.customerservice.customer.application.result;

import com.emmanuel.customerservice.customer.domain.enums.CustomerStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerResult(
        UUID id,
        String fullName,
        String document,
        String email,
        LocalDate birthDate,
        CustomerStatus status,
        String phone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
