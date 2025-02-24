package com.example.backendcreditas.model.loan;

import com.example.backendcreditas.model.customer.Customer;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="customer_id",nullable = false)
    private Customer customer;
    @Enumerated(EnumType.STRING)
    private LoanType loanType;
    private int taxes;

    public Loan(Customer customer, LoanType loanType, int taxes) {
        this.customer = customer;
        this.loanType = loanType;
        this.taxes = taxes;
    }

    public Loan(Optional<Customer> customer, LoanType loanType, int taxes) {
        this.customer = customer.get();
        this.loanType = loanType;
        this.taxes = taxes;
    }
}
