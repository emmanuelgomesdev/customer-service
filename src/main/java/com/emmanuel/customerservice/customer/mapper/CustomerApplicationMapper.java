package com.emmanuel.customerservice.customer.mapper;

import com.emmanuel.customerservice.customer.application.command.CreateCustomerCommand;
import com.emmanuel.customerservice.customer.application.command.UpdateCustomerCommand;
import com.emmanuel.customerservice.customer.application.result.CustomerResult;
import com.emmanuel.customerservice.customer.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerApplicationMapper {

    public Customer toEntity(CreateCustomerCommand command) {
        return Customer.create(
                command.fullName(),
                command.nationality(),
                command.document(),
                command.email(),
                command.phone(),
                command.address(),
                command.gender()
        );
    }

    public CustomerResult toResult(Customer customer){
        return new CustomerResult(
                customer.getId(),
                customer.getFullName(),
                customer.getDocument(),
                customer.getEmail(),
                customer.getNationality(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getGender(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }

   public void updateEntity(Customer customer, UpdateCustomerCommand command){
        customer.update(
                command.fullName(),
                customer.getNationality(),
                command.phone(),
                command.address(),
                command.gender()
        );
   }

}
