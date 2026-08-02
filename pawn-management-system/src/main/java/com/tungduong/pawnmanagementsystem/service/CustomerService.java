package com.tungduong.pawnmanagementsystem.service;

import com.tungduong.pawnmanagementsystem.helper.ResourceNotFoundException;
import com.tungduong.pawnmanagementsystem.model.Customer;
import com.tungduong.pawnmanagementsystem.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository repository;

    public List<Customer> getAllCustomer() {
        return repository.findAll();
    }
    public Customer getCustomerById(Long id){
        return repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Customer not found"));
    }
    public Customer saveCustomer(Customer customer){

        return repository.save(customer);
    }
    public boolean deleteCustomerById(Long id){
        if(!repository.existsById(id)) return false;
        repository.deleteById(id);

        return true;
    }
    public Customer updateCustomer(Customer customer){
        Customer currentCustomer = getCustomerById(customer.getId());

        currentCustomer.setName(customer.getName());
        currentCustomer.setCitizenId(customer.getCitizenId());
        currentCustomer.setEmail(customer.getEmail());
        currentCustomer.setPhone(customer.getPhone());
        currentCustomer.setAddress(customer.getAddress());
        repository.save(currentCustomer);
        return currentCustomer;
    }
}
