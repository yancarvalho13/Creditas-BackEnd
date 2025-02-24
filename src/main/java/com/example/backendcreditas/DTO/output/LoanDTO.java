package com.example.backendcreditas.DTO.output;

import com.example.backendcreditas.DTO.input.CustomerDTO;
import com.example.backendcreditas.model.loan.LoanType;

public record LoanDTO(CustomerDTO customer, LoanType loanType, int taxes) {
}
