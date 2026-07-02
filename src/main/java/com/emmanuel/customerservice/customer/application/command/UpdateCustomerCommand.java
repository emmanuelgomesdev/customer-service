package com.emmanuel.customerservice.customer.application.command;

public record UpdateCustomerCommand(

        String fullName,
        String nationality,
        String phone,
        String address,
        String gender
) {
}
