package com.emmanuel.customerservice.customer.application.command;

import java.time.LocalDate;

public record CreateCustomerCommand(

        String fullName,
        String document,
        String email,
        LocalDate birthDate,
        String phone
) {
}
