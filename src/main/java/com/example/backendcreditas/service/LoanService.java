package com.example.backendcreditas.service;

import com.example.backendcreditas.DTO.input.CustomerDTO;
import com.example.backendcreditas.DTO.output.LoanDTO;
import com.example.backendcreditas.model.customer.Customer;
import com.example.backendcreditas.model.loan.Loan;
import com.example.backendcreditas.model.loan.LoanType;
import com.example.backendcreditas.model.customer.Location;
import com.example.backendcreditas.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private final CustomerService customerService;
    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository, CustomerService customerService) {
        this.loanRepository = loanRepository;
        this.customerService = customerService;
    }

    public void createLoan(LoanDTO loanDTO) {
        Optional<Customer> customer = customerService.customerRepository.findById(loanDTO.customer().id());///Cria um customer com o id a ser vinculado ao Loan
        Loan loan = new Loan(customer, loanDTO.loanType(), loanDTO.taxes());/// Cria o Loan, vinculando ao customer

        loanRepository.save(loan);
    }


    public boolean loanAuthorized(CustomerDTO customer) {
        List<LoanType> avaiableLoans = avaiableLoan(customer);
        return !avaiableLoans.isEmpty();
    }

    public List<LoanType> avaiableLoan(CustomerDTO customer) {
        List<LoanType> avaiableLoans = new ArrayList<>();

        BigDecimal income = customer.income();
        int age = customer.age();
        Location location = customer.location();

        /// Regra Empréstimo Pessoal sempre disponivel
        avaiableLoans.add(LoanType.PERSONAL);

        /// Regra para Com Garantia
        if (income != null) {
            if (income.compareTo(BigDecimal.valueOf(3000)) <= 0 && age < 30 && location.equals(Location.SP)) {
                avaiableLoans.add(LoanType.COLLATERIZED);
            } else if (income.compareTo(BigDecimal.valueOf(5000)) < 0 && location.equals(Location.SP)) {
                avaiableLoans.add(LoanType.COLLATERIZED);
            } else if (income.compareTo(BigDecimal.valueOf(5000)) >= 0 && age < 30) {
                avaiableLoans.add(LoanType.COLLATERIZED);
            }
        }

        /// regra para Consignado
        if (income != null) {
            if (income.compareTo(BigDecimal.valueOf(5000)) >= 0) {
                avaiableLoans.add(LoanType.PAYROLL);
            }
        }

        return avaiableLoans;
    }
}
