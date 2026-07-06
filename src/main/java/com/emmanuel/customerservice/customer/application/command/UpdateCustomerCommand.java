package com.emmanuel.customerservice.customer.application.command;

import java.time.LocalDate;

public record UpdateCustomerCommand(

        String fullName,
        LocalDate birthDate,
        String phone

) {
}
