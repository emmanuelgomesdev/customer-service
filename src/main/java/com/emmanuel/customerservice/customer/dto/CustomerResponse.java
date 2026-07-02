package com.emmanuel.customerservice.customer.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerResponse(

        UUID id,
        String fullName,
        String document,
        String email,
        String nationality,
        String phone,
        String address,
        String gender,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {}
