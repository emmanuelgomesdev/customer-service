package com.emmanuel.customerservice.customer.dto;

import com.emmanuel.customerservice.customer.domain.enums.CustomerStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerResponse(

        UUID id,
        String fullName,
        String document,
        String email,
        LocalDate birthDate,
        CustomerStatus status,
        String phone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {}
