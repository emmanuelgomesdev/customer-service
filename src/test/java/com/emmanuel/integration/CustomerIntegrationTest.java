package com.emmanuel.integration;

import com.emmanuel.customerservice.customer.domain.Customer;
import com.emmanuel.customerservice.customer.domain.enums.CustomerStatus;
import com.emmanuel.customerservice.customer.dto.CreateCustomerRequest;
import com.emmanuel.customerservice.customer.dto.CustomerResponse;
import com.emmanuel.customerservice.customer.dto.UpdateCustomerRequest;
import com.emmanuel.customerservice.customer.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void shouldCreatecustomer() throws Exception {

        //Arrange
        CreateCustomerRequest request = new CreateCustomerRequest(
                "Maria Isabel",
                "111.555.444-99",
                "isabel@email.com",
                LocalDate.parse("1998-06-18"),
                "(00)55996633"
        );

        //Act / Assert HTTP
        MvcResult result = mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fullName").value(request.fullName()))
                .andExpect(jsonPath("$.document").value(request.document()))
                .andExpect(jsonPath("$.email").value(request.email()))
                .andExpect(jsonPath("$.birthDate").value(request.birthDate().toString()))
                .andReturn();

        //Extract response body
        String jsonResponse = result.getResponse().getContentAsString();

        //Convert JSON response to DTO
        CustomerResponse response = objectMapper.readValue(jsonResponse, CustomerResponse.class);

        //Assert database
        Optional<Customer> customerSaved = repository.findById(response.id());

        assertThat(customerSaved).isPresent();
        assertThat(customerSaved.get().getFullName()).isEqualTo(request.fullName());
        assertThat(customerSaved.get().getDocument()).isEqualTo(request.document());
        assertThat(customerSaved.get().getEmail()).isEqualTo(request.email());

    }

    @Test
    void shouldFindcustomerById() throws Exception {

        //Arrange
        Customer customer = Customer.create(
                "Maria Isabel",
                "111.555.444-99",
                "isabel@email.com",
                LocalDate.parse("1998-06-18"),
                "(00)55996633"
        );

        Customer saved = repository.save(customer);

        UUID id = saved.getId();

        //Act/Assert
        mockMvc.perform(get("/customers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.fullName").value(saved.getFullName()))
                .andExpect(jsonPath("$.document").value(saved.getDocument()))
                .andExpect(jsonPath("$.email").value(saved.getEmail()))
                .andExpect(jsonPath("$.birthDate").value(saved.getBirthDate().toString()))
                .andExpect(jsonPath("$.status").value(saved.getStatus().name()))
                .andExpect(jsonPath("$.phone").value(saved.getPhone()));
    }

    @Test
    void shouldFindAllcustomers() throws Exception {

        //Arrange
        Customer customer1 = Customer.create(
                "Maria Isabel",
                "111.555.444-99",
                "isabel@email.com",
                LocalDate.parse("1998-06-18"),
                "(00)55996633"

        );

        Customer customer2 = Customer.create(
                "José Gomes",
                "11.777.999-33",
                "gomes@silva.com",
                LocalDate.parse("1998-06-18"),
                "(00)11554488"

        );


        repository.saveAll(List.of(customer1, customer2));

        //Act/Assert
        mockMvc.perform(get("/customers")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "fullName,asc"))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].fullName").value(customer2.getFullName()))
                .andExpect(jsonPath("$.content[0].document").value(customer2.getDocument()))
                .andExpect(jsonPath("$.content[0].email").value(customer2.getEmail()))
                .andExpect(jsonPath("$.content[0].birthDate").value(customer2.getBirthDate().toString()))
                .andExpect(jsonPath("$.content[0].status").value(customer2.getStatus().name()))
                .andExpect(jsonPath("$.content[1].fullName").value(customer1.getFullName()))
                .andExpect(jsonPath("$.content[1].document").value(customer1.getDocument()))
                .andExpect(jsonPath("$.content[1].email").value(customer1.getEmail()))
                .andExpect(jsonPath("$.content[1].birthDate").value(customer1.getBirthDate().toString()))
                .andExpect(jsonPath("$.content[1].status").value(customer1.getStatus().name()))
                .andExpect(jsonPath("$.page.totalElements").value(2))
                .andExpect(jsonPath("$.page.size").value(5))
                .andExpect(jsonPath("$.page.number").value(0));

    }

    @Test
    void shouldUpdatecustomer() throws Exception {

        //Arrange
        Customer customer = Customer.create(
                "José Gomes",
                "11.777.999-33",
                "gomes@silva.com",
                LocalDate.parse("1998-06-18"),
                "(00)11554488"

        );

        Customer saved = repository.save(customer);

        UpdateCustomerRequest request = new UpdateCustomerRequest(
                "Maria Isabel",
                LocalDate.parse("1998-06-18"),
                "(00)9999-8888"
        );


        //Act/Assert
        mockMvc.perform(put("/customers/{id}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId().toString()))
                .andExpect(jsonPath("$.fullName").value(request.fullName()))
                .andExpect(jsonPath("$.birthDate").value(request.birthDate().toString()))
                .andExpect(jsonPath("$.phone").value(request.phone()));

        Customer updated = repository.findById(saved.getId()).orElseThrow();


        //Assert updated fields
        assertThat(updated.getFullName()).isEqualTo(request.fullName());
        assertThat(updated.getBirthDate()).isEqualTo(request.birthDate());
        assertThat(updated.getPhone()).isEqualTo(request.phone());

        //Assert unchanged fields
        assertThat(updated.getDocument()).isEqualTo(customer.getDocument());
        assertThat(updated.getEmail()).isEqualTo(customer.getEmail());

    }

    @Test
    void shouldDeletecustomer() throws Exception {

        //Arrange
        Customer customer = Customer.create(
                "José Gomes",
                "11.777.999-33",
                "gomes@silva.com",
                LocalDate.parse("1998-06-18"),
                "(00)11554488"
        );

        Customer saved = repository.save(customer);

        //Act/Assert HTTP
        mockMvc.perform(delete("/customers/{id}", saved.getId()))
                .andExpect(status().isNoContent());

        //Assert database
        assertThat(repository.findById(saved.getId())).isEmpty();

    }
}
