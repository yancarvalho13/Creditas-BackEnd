package com.example.backendcreditas.model.customer;

import com.example.backendcreditas.model.loan.Loan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private int age;
    private Location location;
    private BigDecimal income;

    @OneToMany(mappedBy = "customer")
    private Set<Loan> loans;

    public Customer(String name, int age, Location location, BigDecimal income) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.income = income;
    }
    public Customer(UUID id,String name, int age, Location location, BigDecimal income) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.location = location;
        this.income = income;
    }
}
