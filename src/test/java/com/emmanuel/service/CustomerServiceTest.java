package com.emmanuel.service;

import com.emmanuel.customerservice.customer.application.command.CreateCustomerCommand;
import com.emmanuel.customerservice.customer.application.command.UpdateCustomerCommand;
import com.emmanuel.customerservice.customer.application.result.CustomerResult;
import com.emmanuel.customerservice.customer.domain.Customer;
import com.emmanuel.customerservice.customer.domain.enums.CustomerStatus;
import com.emmanuel.customerservice.customer.mapper.CustomerApplicationMapper;
import com.emmanuel.customerservice.customer.repository.CustomerRepository;
import com.emmanuel.customerservice.customer.service.CustomerService;
import com.emmanuel.customerservice.customer.validation.CustomerValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerValidator validator;

    @Mock
    private CustomerApplicationMapper applicationMapper;

    @InjectMocks
    private CustomerService service;

    @Test
    void shouldCreatecustomer() {

        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        CreateCustomerCommand command = new CreateCustomerCommand(
                "José Silva",
                "11.222.333-44",
                "silva@email.com",
                LocalDate.parse("1998-06-18"),
                "(00)22336699"
        );

        Customer customer = Customer.create(
                command.fullName(),
                command.document(),
                command.email(),
                command.birthDate(),
                command.phone()
        );

        CustomerResult result = new CustomerResult(
                id,
                command.fullName(),
                command.document(),
                command.email(),
                command.birthDate(),
                CustomerStatus.ACTIVE,
                command.phone(),
                now,
                now
        );

        when(applicationMapper.toEntity(command)).thenReturn(customer);
        when(repository.save(customer)).thenReturn(customer);
        when(applicationMapper.toResult(customer)).thenReturn(result);

        CustomerResult response = service.create(command);

        assertThat(response).isNotNull();
        assertThat(response.fullName()).isEqualTo(command.fullName());
        assertThat(response.document()).isEqualTo(command.document());
        assertThat(response.email()).isEqualTo(command.email());

        verify(validator).validateDocumentDoesNotExist(command.document());
        verify(applicationMapper).toEntity(command);
        verify(repository).save(customer);
        verify(applicationMapper).toResult(customer);
    }

    @Test
    void shouldFindcustomerById() {

        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Customer customer = Customer.create(
                "Maria Isabel",
                "11.555.444-99",
                "isabel@email.com",
                LocalDate.parse("1998-06-18"),
                "(44)55996633"
        );

        CustomerResult result = new CustomerResult(
                id,
                customer.getFullName(),
                customer.getDocument(),
                customer.getEmail(),
                customer.getBirthDate(),
                CustomerStatus.ACTIVE,
                customer.getPhone(),
                now,
                now
        );

        when(validator.findByIdOrThrow(id)).thenReturn(customer);
        when(applicationMapper.toResult(customer)).thenReturn(result);

        CustomerResult response = service.findById(id);

        assertThat(response.fullName()).isEqualTo(customer.getFullName());
        assertThat(response.document()).isEqualTo(customer.getDocument());
        assertThat(response.email()).isEqualTo(customer.getEmail());

        verify(validator).findByIdOrThrow(id);
        verify(applicationMapper).toResult(customer);
    }

    @Test
    void shouldFindAllcustomer() {

        Pageable pageable = PageRequest.of(
                0,
                5,
                Sort.by("fullName").ascending()
        );

        LocalDateTime now = LocalDateTime.now();

        Customer customer1 = Customer.create(
                "Maria Silva",
                "00.111.222-33",
                "maria@silva.com",
                LocalDate.parse("1998-06-18"),
                "(00)22339988"
        );

        Customer customer2 = Customer.create(
                "José Gomes",
                "11.777.999-33",
                "gomes@silva.com",
                LocalDate.parse("1998-06-18"),
                "(00)11554488"
        );

        CustomerResult result1 = new CustomerResult(
                UUID.randomUUID(),
                customer1.getFullName(),
                customer1.getDocument(),
                customer1.getEmail(),
                customer1.getBirthDate(),
                CustomerStatus.ACTIVE,
                customer1.getPhone(),
                now,
                now
        );

        CustomerResult result2 = new CustomerResult(
                UUID.randomUUID(),
                customer2.getFullName(),
                customer2.getDocument(),
                customer2.getEmail(),
                customer2.getBirthDate(),
                CustomerStatus.ACTIVE,
                customer2.getPhone(),
                now,
                now
        );

        Page<Customer> customerPage = new PageImpl<>(List.of(customer1, customer2), pageable, 2);

        when(repository.findAll(pageable)).thenReturn(customerPage);
        when(applicationMapper.toResult(customer1)).thenReturn(result1);
        when(applicationMapper.toResult(customer2)).thenReturn(result2);

        Page<CustomerResult> response = service.findAll(pageable);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getTotalElements()).isEqualTo(2);
        assertThat(response.getNumber()).isEqualTo(0);
        assertThat(response.getSize()).isEqualTo(5);

        assertThat(response.getContent())
                .extracting("fullName", "email")
                .containsExactlyInAnyOrder(
                        tuple("Maria Silva", "maria@silva.com"),
                        tuple("José Gomes", "gomes@silva.com")
                );

        verify(repository).findAll(pageable);
        verify(applicationMapper).toResult(customer1);
        verify(applicationMapper).toResult(customer2);
    }

    @Test
    void shouldUpdatecustomer() {

        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Customer customer = Customer.create(
                "Maria Silva",
                "00.111.222-33",
                "maria@silva.com",
                LocalDate.parse("1998-06-18"),
                "(00)22339988"
        );

        UpdateCustomerCommand command = new UpdateCustomerCommand(
                "José Gomes",
                LocalDate.parse("1998-06-18"),
                "(00)11554488"

        );

        CustomerResult result = new CustomerResult(
                id,
                command.fullName(),
                customer.getDocument(),
                customer.getEmail(),
                command.birthDate(),
                CustomerStatus.ACTIVE,
                command.phone(),
                now,
                now
        );

        when(validator.findByIdOrThrow(id)).thenReturn(customer);
        when(applicationMapper.toResult(customer)).thenReturn(result);

        CustomerResult response = service.update(id, command);

        assertThat(response.fullName()).isEqualTo(command.fullName());
        assertThat(response.phone()).isEqualTo(command.phone());

        verify(validator).findByIdOrThrow(id);
        verify(applicationMapper).toResult(customer);
    }

    @Test
    void shouldDeletecustomer() {

        UUID id = UUID.randomUUID();

        Customer customer = Customer.create(
                "Maria Isabel",
                "11.555.444-99",
                "isabel@email.com",
                LocalDate.parse("1998-06-18"),
                "(44)55996633"

        );

        when(validator.findByIdOrThrow(id)).thenReturn(customer);

        service.delete(id);

        verify(validator).findByIdOrThrow(id);
        verify(repository).delete(customer);
    }
}