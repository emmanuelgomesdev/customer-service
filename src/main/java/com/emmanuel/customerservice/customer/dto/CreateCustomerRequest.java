package com.emmanuel.customerservice.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateCustomerRequest(

        @NotBlank(message = "First name is required")
        @Size(max = 80, message ="Full name must have at most 80 characters")
        String fullName,

        @NotBlank(message = "Document is required")
        @Size(min= 11 , max = 15, message = "Document must have between 11 and 15 characters")
        String document,

        @NotBlank(message = "Email is required")
        @Email(message = "Email invalid")
        String email,

        @Size(max = 100)
        String nationality,

        @NotNull(message = "Birth date is required")
        LocalDate birthDate,

        @NotBlank(message = "Phone is required")
        @Size(max = 15, message = "Phone must have at most 15 characters")
        String phone,

        @NotBlank(message = "Address is required")
        @Size(max = 255, message = "Address must have at most 255 characters")
        String address,

        @NotBlank(message = "Gender is required")
        @Size(max = 20, message = "Gender must have at most 20 characters")
        String gender
) {
}
