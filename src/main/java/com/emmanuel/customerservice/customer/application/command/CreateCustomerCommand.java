package com.emmanuel.customerservice.customer.application.command;

import java.time.LocalDate;

public record CreateCustomerCommand(

        String fullName,
        String document,
        String email,
        String nationality,
        LocalDate birthDate,
        String phone,
        String address,
        String gender
) {
}
