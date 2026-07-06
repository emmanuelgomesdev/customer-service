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

        @NotNull(message = "Birth date is required")
        LocalDate birthDate,

        @NotBlank(message = "Phone is required")
        @Size(max = 15, message = "Phone must have at most 15 characters")
        String phone

){}
