package com.emmanuel.customerservice.customer.mapper;

import com.emmanuel.customerservice.customer.application.command.CreateCustomerCommand;
import com.emmanuel.customerservice.customer.application.command.UpdateCustomerCommand;
import com.emmanuel.customerservice.customer.application.result.CustomerResult;
import com.emmanuel.customerservice.customer.domain.Customer;
import com.emmanuel.customerservice.customer.domain.enums.CustomerStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomerApplicationMapper {

    public Customer toEntity(CreateCustomerCommand command) {
        return Customer.create(
                command.fullName(),
                command.document(),
                command.email(),
                command.birthDate(),
                command.phone()
        );
    }

    public CustomerResult toResult(Customer customer){
        return new CustomerResult(
                customer.getId(),
                customer.getFullName(),
                customer.getDocument(),
                customer.getEmail(),
                customer.getBirthDate(),
                customer.getStatus(),
                customer.getPhone(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }

   public void updateEntity(Customer customer, UpdateCustomerCommand command){
        customer.update(
                command.fullName(),
                command.birthDate(),
                command.phone()

        );
   }

}
