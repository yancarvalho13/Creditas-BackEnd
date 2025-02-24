package com.example.backendcreditas.service;

import com.example.backendcreditas.DTO.input.CustomerDTO;
import com.example.backendcreditas.model.customer.Customer;
import com.example.backendcreditas.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public CustomerDTO findCustomerByName(String name) {
        Customer customer = customerRepository.findByName(name);
        return new CustomerDTO(customer.getId(),customer.getName(), customer.getAge(),customer.getLocation(), customer.getIncome());
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> new CustomerDTO(customer.getId(),customer.getName(),customer.getAge(),customer.getLocation(),customer.getIncome()))
                .toList();
    }
}
