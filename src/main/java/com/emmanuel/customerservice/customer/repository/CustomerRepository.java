package com.emmanuel.customerservice.customer.repository;

import com.emmanuel.customerservice.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Boolean existsByDocument(String document);
}
