package com.emmanuel.customerservice.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateCustomerRequest(
        @NotBlank(message = "First name is required")
        @Size(max = 80, message = "First name must have at most 80 characters")
        String fullName,

        @NotNull(message = "Birth date is required")
        LocalDate birthDate,

        @NotBlank(message = "Phone is required")
        @Size(max = 15, message = "Phone must have at most 15 characters")
        String phone

) {
}
