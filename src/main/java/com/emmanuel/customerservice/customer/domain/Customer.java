package com.emmanuel.customerservice.customer.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @Column(name = "nationality", length = 100)
    private String nationality;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "gender", nullable = false)
    private String gender;

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
            String nationality,
            String phone,
            String address,
            String gender) {

        Customer customer = new Customer();

        customer.fullName = fullName;
        customer.document = document;
        customer.email = email;
        customer.nationality = nationality;
        customer.phone = phone;
        customer.address = address;
        customer.gender = gender;

        return customer;
    }

    public void update(
            String fullName,
            String nationality,
            String phone,
            String address,
            String gender) {

        this.fullName = fullName;
        this.nationality = nationality;
        this.phone = phone;
        this.address = address;
        this.gender = gender;

    }


    public UUID getId() {return id;}

    public String getFullName() {return fullName;}

    public String getDocument() {return document;}

    public String getEmail() {return email;}

    public String getNationality() {return nationality;}

    public String getPhone() {return phone;}

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public LocalDateTime getCreatedAt() {return createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
}
