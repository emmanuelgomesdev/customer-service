package com.emmanuel.repository;

import com.emmanuel.customerservice.customer.domain.Customer;
import com.emmanuel.customerservice.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DataJpaTest
class customerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    Customer customer;

    @BeforeEach
    void setup() {
        customer = Customer.create(
                "José Silva",
                "123.456.789-00",
                "silva@email.com",
                LocalDate.parse("1998-06-18"),
                "(11)2233-4455"
        );
    }

    @Test
    void shouldSavecustomer() {

        //Arrange
        //Feito no @BeforeEach

        //Act
        Customer saved = repository.save(customer);

        Customer found = repository.findById(saved.getId())
                .orElseThrow();

        //Assert
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();

        assertThat(saved.getDocument()).isEqualTo(customer.getDocument());
        assertThat(saved.getFullName()).isEqualTo(customer.getFullName());
        assertThat(saved.getEmail()).isEqualTo(customer.getEmail());
        assertThat(saved.getBirthDate()).isEqualTo(customer.getBirthDate());
        assertThat(saved.getStatus()).isEqualTo(customer.getStatus());
        assertThat(saved.getPhone()).isEqualTo(customer.getPhone());
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getDocument()).isEqualTo(saved.getDocument());

    }

    @Test
    void shouldFindcustomerById() {

        //Arrange
        //Feito no @BeforeEach

        //Act
        Customer save = repository.save(customer);

        Customer found = repository.findById(save.getId())
                .orElseThrow();

        //Assert
        assertThat(found.getId()).isEqualTo(save.getId());
        assertThat(found.getDocument()).isEqualTo(save.getDocument());
        assertThat(found.getFullName()).isEqualTo(save.getFullName());

    }

    @Test
    void shouldFindAllcustomers() {

        //Arrange
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

        repository.save(customer1);
        repository.save(customer2);

        //Act
        List<Customer> found = repository.findAll();

        //Assert
        assertThat(found).isNotEmpty();
        assertThat(found).hasSize(2);

        assertThat(found)
                .extracting(Customer::getDocument)
                .containsExactlyInAnyOrder(
                        "00.111.222-33",
                        "11.777.999-33"
                );

    }

    @Test
    void shouldReturnTrueWhenDocumentExists() {

        //Arrange
        //Feito no @BeforeEach

        //Act
        Customer saved = repository.save(customer);
        boolean exists = repository.existsByDocument(saved.getDocument());

        //Assert
        assertThat(exists).isTrue();

    }

    @Test
    void shouldReturnFalseWhenDocumentDoesNotExist() {

        //Arrange
        //Feito no @BeforeEach

        //Act
        repository.save(customer);
        boolean exists = repository.existsByDocument(("99.999.999-99"));

        //Assert
        assertThat(exists).isFalse();

    }

    @Test
    void shouldDeletecustomer() {

        //Arrange
        //Feito no BeforeEach

        //Act
        Customer save = repository.save(customer);
        repository.delete(save);

        Optional<Customer> found = repository.findById(save.getId());

        //Assert
        assertThat(found).isEmpty();

    }

    @Test
    void shouldNotSaveDuplicateDocument() {

        //Arrange
        //Feito no @BeforeEach

        repository.save(customer);

        Customer duplicatecustomer = Customer.create(
                "Joana Silva",
                customer.getDocument(),
                "joana@gmail.com",
                LocalDate.parse("1998-06-18"),
                "(11)8889996"
        );

        //Act/Assert

        assertThatThrownBy(() -> repository.saveAndFlush(duplicatecustomer))
                .isInstanceOf(DataIntegrityViolationException.class);


    }


}
