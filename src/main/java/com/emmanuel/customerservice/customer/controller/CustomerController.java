package com.emmanuel.customerservice.customer.controller;


import com.emmanuel.customerservice.customer.application.result.CustomerResult;
import com.emmanuel.customerservice.customer.dto.CreateCustomerRequest;
import com.emmanuel.customerservice.customer.dto.CustomerResponse;
import com.emmanuel.customerservice.customer.dto.UpdateCustomerRequest;
import com.emmanuel.customerservice.customer.mapper.CustomerRestMapper;
import com.emmanuel.customerservice.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;
import java.util.UUID;

@Tag(name = "customers", description = "Endpoints for customer management")
@RestController
@RequestMapping(value = "/customers",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {


    private final CustomerService service;
    private final CustomerRestMapper customerRestMapper;

    public CustomerController(CustomerService service, CustomerRestMapper customerRestMapper) {
        this.service = service;
        this.customerRestMapper = customerRestMapper;
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create customer",
            description = "Creates a new customer"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Customer already exists")
    })
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CreateCustomerRequest request) {

        var command = customerRestMapper.toCommand(request);
        CustomerResult result = service.create(command);
        CustomerResponse response = customerRestMapper.toResponse(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping(value = "/{id}")
    @Operation(summary = "Find customer by id",
            description = "Finds customer by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CustomerResponse> findById(@PathVariable UUID id) {

        var result = service.findById(id);
        var response = customerRestMapper.toResponse(result);

        return ResponseEntity.ok(response);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update customer",
            description = "Updates an existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Customer not found")

    })
    public ResponseEntity<CustomerResponse> update(
            @PathVariable UUID id, @Valid
            @RequestBody UpdateCustomerRequest dto) {

        var command = customerRestMapper.toUpdateCommand(dto);
        var result = service.update(id, command);
        var response = customerRestMapper.toResponse(result);

        return ResponseEntity.ok(response);
    }


    @GetMapping
    @Operation(summary = "Find all customers",
            description = "Finds customers using pagination parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customers found"),
    })
    public Page<CustomerResponse> findAll(
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "fullName"
            ) Pageable pageable) {

        var result = service.findAll(pageable);

        return result.map(customerRestMapper::toResponse);
    }


    @PatchMapping(value = "/{id}/deactivate")
    @Operation(summary = "Deactivate customer",
            description = "Deactivates an active customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customer deactivate"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "409", description = "Customer already inactive")
    })
    public ResponseEntity<CustomerResponse> deactivate(@PathVariable UUID id) {

        var result  = service.deactivate(id);
        var response = customerRestMapper.toResponse(result);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "/{id}/activate")
    @Operation(summary = "Activate customer",
            description = "Activates an inactive customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customer activate"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "409", description = "Customer already active")
    })
    public ResponseEntity<CustomerResponse> activate(@PathVariable UUID id) {

       var result = service.activate(id);
       var response = customerRestMapper.toResponse(result);
        return ResponseEntity.ok(response);
    }

}
