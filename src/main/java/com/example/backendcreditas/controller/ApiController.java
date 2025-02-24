package com.example.backendcreditas.controller;

import com.example.backendcreditas.DTO.input.CustomerDTO;
import com.example.backendcreditas.DTO.input.CustomerLoanContractRequest;
import com.example.backendcreditas.DTO.output.CustomerLoanListOutput;
import com.example.backendcreditas.DTO.output.CustomerLoanOutput;
import com.example.backendcreditas.DTO.output.LoanDTO;
import com.example.backendcreditas.model.customer.Customer;
import com.example.backendcreditas.model.loan.LoanType;
import com.example.backendcreditas.service.CustomerService;
import com.example.backendcreditas.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class ApiController {
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    private final CustomerService customerService;
    private final LoanService loanService;

    public ApiController(CustomerService customerService, LoanService loanService) {
        this.customerService = customerService;
        this.loanService = loanService;
    }

    @PostMapping("/available")
    public ResponseEntity<CustomerLoanListOutput> createCustomer(@Validated @RequestBody CustomerDTO request) {
        Customer customer = request.tocCustomer(); ///Transforma o Input Json em um Customer

        customer = customerService.createCustomer(customer); ///Persiste o Customer no banco de dados


        List<LoanType> availableLoans = loanService.avaiableLoan(request);

        /// Lista de Empréstimos disponíveis, contendo o nome do emprestimo e a taxa
        List<CustomerLoanOutput> loans = availableLoans.stream()
                .map(loan -> new CustomerLoanOutput(loan.name().toLowerCase(), loan.getLoanTax()))
                .toList();

        /// Output da requisição, retornando o nome do cliente e a lista de empréstimos aprovados
        CustomerLoanListOutput response = new CustomerLoanListOutput(customer.getName(), loans);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/listUsers")
    public ResponseEntity<List<CustomerDTO>> listUsers() {
        List<CustomerDTO> customerList = customerService.getAllCustomers();
        return ResponseEntity.ok(customerList);
    }

    @GetMapping("/available/{name}")
    public ResponseEntity<CustomerLoanListOutput> getCustomerAvailableLoans(@PathVariable String name) {
        CustomerDTO customer = customerService.findCustomerByName(name);

        List<LoanType> availableLoans = loanService.avaiableLoan(customer);

        /// Lista de Empréstimos disponíveis, contendo o nome do emprestimo e a taxa
        List<CustomerLoanOutput> loans = availableLoans.stream()
                .map(loan -> new CustomerLoanOutput(loan.name().toLowerCase(), loan.getLoanTax()))
                .toList();

        /// Output da requisição, retornando o nome do cliente e a lista de empréstimos aprovados
        CustomerLoanListOutput response = new CustomerLoanListOutput(customer.name(), loans);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/contract/{customer}")
    public ResponseEntity<String> createContract(@RequestBody CustomerLoanContractRequest loanRequest, @PathVariable String customer) {
        CustomerDTO customerDTO = customerService.findCustomerByName(customer); /// Procura o Customer passado na URL

        List<LoanType> availableLoans = loanService.avaiableLoan(customerDTO); /// Verifica os empréstimos disponiveis ao usuário

        LoanType loanType = LoanType.valueOf(loanRequest.loanType().toUpperCase());

        if (!availableLoans.contains(loanType)) { /// Verifica se o empréstimo solicitado esta disponível para o usuario
            return ResponseEntity.badRequest().body("Empréstimo não disponível");
        }

        LoanDTO loanDTO = new LoanDTO(customerDTO, loanType, loanType.getLoanTax());

        loanService.createLoan(loanDTO);

        return ResponseEntity.ok("Contrato criado com sucesso");
    }
}