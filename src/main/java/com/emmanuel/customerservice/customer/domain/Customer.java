package com.emmanuel.customerservice.customer.domain;

import com.emmanuel.customerservice.customer.domain.enums.CustomerStatus;
import com.emmanuel.customerservice.exception.BusinessException;
import com.emmanuel.customerservice.exception.ErrorResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "document", nullable = false, unique = true)
    private String document;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @Column(name = "phone", nullable = false)
    private String phone;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public static Customer create(
            String fullName,
            String document,
            String email,
            LocalDate birthDate,
            String phone
    ) {

        Customer customer = new Customer();

        customer.fullName = fullName;
        customer.document = document;
        customer.email = email;
        customer.birthDate = birthDate;
        customer.status = CustomerStatus.ACTIVE;
        customer.phone = phone;

        return customer;
    }

    public void update(
            String fullName,
            LocalDate birthDate,
            String phone
    ) {

        this.fullName = fullName;
        this.birthDate = birthDate;
        this.phone = phone;

    }


    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDocument() {
        return document;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    public void deactivate(){
        if(status == CustomerStatus.INACTIVE){
            throw new BusinessException(ErrorResponse.CUSTOMER_ALREADY_INACTIVE);
        }
        this.status = CustomerStatus.INACTIVE;
    }

    public void activate(){
        if(status == CustomerStatus.ACTIVE){
            throw new BusinessException(ErrorResponse.CUSTOMER_ALREADY_ACTIVE);
        }
        this.status = CustomerStatus.ACTIVE;
    }

}
