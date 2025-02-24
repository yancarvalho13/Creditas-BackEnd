package com.example.backendcreditas.DTO.input;

import com.example.backendcreditas.model.customer.Customer;
import com.example.backendcreditas.model.customer.Location;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.UUID;

public record CustomerDTO(
        @JsonIgnore
        UUID id,
        String name,
        int age,
        Location location,
        BigDecimal income) {

    public Customer tocCustomer(){
        return new Customer(this.name, this.age, this.location, this.income);
    }
}
