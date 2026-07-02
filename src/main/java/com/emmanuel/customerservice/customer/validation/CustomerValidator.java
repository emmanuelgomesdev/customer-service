package com.emmanuel.customerservice.customer.validation;

import com.emmanuel.customerservice.exception.BusinessException;
import com.emmanuel.customerservice.exception.ErrorResponse;
import com.emmanuel.customerservice.customer.domain.Customer;
import com.emmanuel.customerservice.customer.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerValidator {

    private final CustomerRepository repository;

    public CustomerValidator(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer findByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorResponse.CUSTOMER_NOT_FOUND));
    }

    public void validateDocumentDoesNotExist(String document) {
        if (repository.existsByDocument(document)) {
            throw new BusinessException(ErrorResponse.CUSTOMER_ALREADY_EXISTS);
        }

    }


}
