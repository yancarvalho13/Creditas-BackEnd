package com.example.backendcreditas.DTO.output;

import java.util.List;

public record CustomerLoanListOutput(String customer, List<CustomerLoanOutput> loans) {
}
