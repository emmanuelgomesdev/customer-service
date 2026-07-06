package com.emmanuel.customerservice.customer.service;

import com.emmanuel.customerservice.customer.application.command.CreateCustomerCommand;
import com.emmanuel.customerservice.customer.application.command.UpdateCustomerCommand;
import com.emmanuel.customerservice.customer.application.result.CustomerResult;
import com.emmanuel.customerservice.customer.domain.Customer;
import com.emmanuel.customerservice.customer.mapper.CustomerApplicationMapper;
import com.emmanuel.customerservice.customer.repository.CustomerRepository;
import com.emmanuel.customerservice.customer.validation.CustomerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository repository;
    private final CustomerValidator validator;
    private final CustomerApplicationMapper applicationMapper;

    public CustomerService(CustomerRepository repository,
                           CustomerValidator validator,
                           CustomerApplicationMapper applicationMapper) {
        this.repository = repository;
        this.validator = validator;
        this.applicationMapper = applicationMapper;
    }


    @Transactional
    public CustomerResult create(CreateCustomerCommand command) {
        LOGGER.info("Creating customer with name {}", command.fullName());

        validator.validateDocumentDoesNotExist(command.document());

        Customer customer = applicationMapper.toEntity(command);
        Customer saved = repository.save(customer);
        return applicationMapper.toResult(saved);

    }

    @Transactional(readOnly = true)
    public CustomerResult findById(UUID id) {
        LOGGER.info("Finding customer with id {}", id);

        var customer = validator.findByIdOrThrow(id);
        return applicationMapper.toResult(customer);

    }

    @Transactional(readOnly = true)
    public Page<CustomerResult> findAll(Pageable pageable) {
        LOGGER.info("Finding customers with pageable {}", pageable);

        return repository
                .findAll(pageable)
                .map(applicationMapper::toResult);
    }

    @Transactional
    public CustomerResult update(UUID id, UpdateCustomerCommand command) {
        LOGGER.info("Updating customer with id {}", id);

        var customer = validator.findByIdOrThrow(id);
        applicationMapper.updateEntity(customer, command);
        return applicationMapper.toResult(customer);


    }

    @Transactional
    public CustomerResult deactivate(UUID id) {
        LOGGER.info("Deactivating customer with id {}", id);

        var customer = validator.findByIdOrThrow(id);
        customer.deactivate();
        return applicationMapper.toResult(customer);

    }

    @Transactional
    public CustomerResult activate(UUID id) {
        LOGGER.info("Activating customer with id {}", id);

        var customer = validator.findByIdOrThrow(id);
        customer.activate();
        return applicationMapper.toResult(customer);
    }

}
