package com.tungduong.pawnmanagementsystem.service;

import com.tungduong.pawnmanagementsystem.model.Customer;
import com.tungduong.pawnmanagementsystem.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> getAllCustomer() {
        return repository.findAll();
    }
    public Customer getCustomerById(Long id){
        return repository.findById(id).orElse(null);
    }
    public Customer createCustomer(Customer customer){
        return repository.save(customer);
    }
    public boolean deleteCustomer(Long id){
        return repository.delete(id);
    }
    public Customer updateCustomer(Customer customer){
        return repository.update(customer);
    }
}
