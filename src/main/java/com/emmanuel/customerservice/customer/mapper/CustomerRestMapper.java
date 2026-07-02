package com.emmanuel.customerservice.customer.mapper;

import com.emmanuel.customerservice.customer.application.command.CreateCustomerCommand;
import com.emmanuel.customerservice.customer.application.command.UpdateCustomerCommand;
import com.emmanuel.customerservice.customer.application.result.CustomerResult;
import com.emmanuel.customerservice.customer.dto.CreateCustomerRequest;
import com.emmanuel.customerservice.customer.dto.UpdateCustomerRequest;
import com.emmanuel.customerservice.customer.dto.CustomerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerRestMapper {

    CreateCustomerCommand toCommand(CreateCustomerRequest request);

    UpdateCustomerCommand toUpdateCommand(UpdateCustomerRequest request);

    CustomerResponse toResponse(CustomerResult result);
}
