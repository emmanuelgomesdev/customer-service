package com.emmanuel.customerservice.customer.application.command;

public record CreateCustomerCommand(

        String fullName,
        String document,
        String email,
        String nationality,
        String phone,
        String address,
        String gender
) {
}
