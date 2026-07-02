package com.emmanuel.controller;

import com.emmanuel.customerservice.customer.application.command.CreateCustomerCommand;
import com.emmanuel.customerservice.customer.application.command.UpdateCustomerCommand;
import com.emmanuel.customerservice.customer.application.result.CustomerResult;
import com.emmanuel.customerservice.customer.controller.CustomerController;
import com.emmanuel.customerservice.customer.dto.CreateCustomerRequest;
import com.emmanuel.customerservice.customer.dto.CustomerResponse;
import com.emmanuel.customerservice.customer.dto.UpdateCustomerRequest;
import com.emmanuel.customerservice.customer.mapper.CustomerRestMapper;
import com.emmanuel.customerservice.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CustomerService service;

    @MockitoBean
    private CustomerRestMapper customerRestMapper;

    @MockitoBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    void shouldCreatecustomer() throws Exception {

        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        CreateCustomerRequest request = new CreateCustomerRequest(
                "Maria Isabel",
                "111.555.444-99",
                "isabel@email.com",
                "(00)55996633",
                "Rua 5",
                "Feminino"
        );

        CreateCustomerCommand command = new CreateCustomerCommand(
                request.fullName(),
                request.document(),
                request.email(),
                request.phone(),
                request.address(),
                request.gender()
        );

        CustomerResult result = new CustomerResult(
                id,
                request.fullName(),
                request.document(),
                request.email(),
                request.phone(),
                request.address(),
                request.gender(),
                now,
                now
        );

        CustomerResponse response = new CustomerResponse(
                id,
                result.fullName(),
                result.document(),
                result.email(),
                result.phone(),
                result.address(),
                result.gender(),
                result.createdAt(),
                result.updatedAt()
        );

        when(customerRestMapper.toCommand(any(CreateCustomerRequest.class))).thenReturn(command);
        when(service.create(command)).thenReturn(result);
        when(customerRestMapper.toResponse(result)).thenReturn(response);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.id().toString()))
                .andExpect(jsonPath("$.fullName").value(response.fullName()))
                .andExpect(jsonPath("$.document").value(response.document()))
                .andExpect(jsonPath("$.email").value(response.email()))
                .andExpect(jsonPath("$.phone").value(response.phone()))
                .andExpect(jsonPath("$.address").value(response.address()))
                .andExpect(jsonPath("$.gender").value(response.gender()));

        verify(customerRestMapper).toCommand(any(CreateCustomerRequest.class));
        verify(service).create(command);
        verify(customerRestMapper).toResponse(result);
    }

    @Test
    void shouldFindcustomerById() throws Exception {

        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        CustomerResult result = new CustomerResult(
                id,
                "Maria Isabel",
                "111.555.444-99",
                "isabel@email.com",
                "(00)55996633",
                "Rua 5",
                "Feminino",
                now,
                now
        );

        CustomerResponse response = new CustomerResponse(
                id,
                result.fullName(),
                result.document(),
                result.email(),
                result.phone(),
                result.address(),
                result.gender(),
                result.createdAt(),
                result.updatedAt()
        );

        when(service.findById(id)).thenReturn(result);
        when(customerRestMapper.toResponse(result)).thenReturn(response);

        mockMvc.perform(get("/customers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.id().toString()))
                .andExpect(jsonPath("$.fullName").value(response.fullName()))
                .andExpect(jsonPath("$.document").value(response.document()))
                .andExpect(jsonPath("$.email").value(response.email()))
                .andExpect(jsonPath("$.phone").value(response.phone()))
                .andExpect(jsonPath("$.address").value(response.address()))
                .andExpect(jsonPath("$.gender").value(response.gender()));

        verify(service).findById(id);
        verify(customerRestMapper).toResponse(result);
    }

    @Test
    void shouldFindAllcustomer() throws Exception {

        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Pageable pageable = PageRequest.of(
                0,
                5,
                Sort.by("fullName").ascending()
        );

        CustomerResult result1 = new CustomerResult(
                id1,
                "Maria Isabel",
                "111.555.444-99",
                "isabel@email.com",
                "(00)55996633",
                "Rua 5",
                "Feminino",
                now,
                now
        );

        CustomerResult result2 = new CustomerResult(
                id2,
                "José Gomes",
                "11.777.999-33",
                "gomes@silva.com",
                "(00)11554488",
                "Rua 3",
                "Masculino",
                now,
                now
        );

        CustomerResponse response1 = new CustomerResponse(
                id1,
                result1.fullName(),
                result1.document(),
                result1.email(),
                result1.phone(),
                result1.address(),
                result1.gender(),
                result1.createdAt(),
                result1.updatedAt()
        );

        CustomerResponse response2 = new CustomerResponse(
                id2,
                result2.fullName(),
                result2.document(),
                result2.email(),
                result2.phone(),
                result2.address(),
                result2.gender(),
                result2.createdAt(),
                result2.updatedAt()
        );

        List<CustomerResult> results = List.of(result1, result2);

        Page<CustomerResult> page = new PageImpl<>(results, pageable, results.size());

        when(service.findAll(any(Pageable.class))).thenReturn(page);
        when(customerRestMapper.toResponse(result1)).thenReturn(response1);
        when(customerRestMapper.toResponse(result2)).thenReturn(response2);

        mockMvc.perform(get("/customers")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "fullName,asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(response1.id().toString()))
                .andExpect(jsonPath("$.content[0].fullName").value(response1.fullName()))
                .andExpect(jsonPath("$.content[0].email").value(response1.email()))
                .andExpect(jsonPath("$.content[1].id").value(response2.id().toString()))
                .andExpect(jsonPath("$.content[1].fullName").value(response2.fullName()))
                .andExpect(jsonPath("$.content[1].email").value(response2.email()))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.size").value(5))
                .andExpect(jsonPath("$.number").value(0));

        verify(service).findAll(any(Pageable.class));
        verify(customerRestMapper).toResponse(result1);
        verify(customerRestMapper).toResponse(result2);
    }

    @Test
    void shouldUpdatecustomer() throws Exception {

        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        UpdateCustomerRequest request = new UpdateCustomerRequest(
                "José Luís",
                "(22)3366-9966",
                "Rua Beija-Flor 18",
                "Masculino"
        );

        UpdateCustomerCommand command = new UpdateCustomerCommand(
                request.fullName(),
                request.phone(),
                request.address(),
                request.gender()
        );

        CustomerResult result = new CustomerResult(
                id,
                request.fullName(),
                "11.777.999-33",
                "gomes@silva.com",
                request.phone(),
                request.address(),
                request.gender(),
                now,
                now
        );

        CustomerResponse response = new CustomerResponse(
                id,
                result.fullName(),
                result.document(),
                result.email(),
                result.phone(),
                result.address(),
                result.gender(),
                result.createdAt(),
                result.updatedAt()
        );

        when(customerRestMapper.toUpdateCommand(any(UpdateCustomerRequest.class))).thenReturn(command);
        when(service.update(eq(id), eq(command))).thenReturn(result);
        when(customerRestMapper.toResponse(result)).thenReturn(response);

        mockMvc.perform(put("/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.id().toString()))
                .andExpect(jsonPath("$.fullName").value(response.fullName()))
                .andExpect(jsonPath("$.document").value(response.document()))
                .andExpect(jsonPath("$.email").value(response.email()))
                .andExpect(jsonPath("$.phone").value(response.phone()))
                .andExpect(jsonPath("$.address").value(response.address()))
                .andExpect(jsonPath("$.gender").value(response.gender()));

        verify(customerRestMapper).toUpdateCommand(any(UpdateCustomerRequest.class));
        verify(service).update(eq(id), eq(command));
        verify(customerRestMapper).toResponse(result);
    }

    @Test
    void shouldDeletecustomer() throws Exception {

        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/customers/{id}", id))
                .andExpect(status().isNoContent());

        verify(service).delete(id);
    }
}