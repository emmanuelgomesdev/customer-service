package com.emmanuel.customerservice.customer.application.result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerResult(
        UUID id,
        String fullName,
        String document,
        String email,
        String nationality,
        LocalDate birthDate,
        String phone,
        String address,
        String gender,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
