package com.emmanuel.customerservice.customer.application.command;

import java.time.LocalDate;

public record UpdateCustomerCommand(

        String fullName,
        String nationality,
        LocalDate birthDate,
        String phone,
        String address,
        String gender
) {
}
