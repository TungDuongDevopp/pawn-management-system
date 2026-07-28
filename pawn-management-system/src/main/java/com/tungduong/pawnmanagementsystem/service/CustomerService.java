package com.tungduong.pawnmanagementsystem.service;

import com.tungduong.pawnmanagementsystem.model.Account;
import com.tungduong.pawnmanagementsystem.model.Customer;
import com.tungduong.pawnmanagementsystem.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> getAllCustomer() {
        return repository.findAll();
    }
    public Optional<Customer> getCustomerById(Long id){
        return repository.findById(id);
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
        Optional<Customer> optional = getCustomerById(customer.getId());

        if(optional.isEmpty()){
            return null;
        }

        Customer currentCustomer = optional.get();
        currentCustomer.setName(customer.getName());
        currentCustomer.setCitizenId(customer.getCitizenId());
        currentCustomer.setEmail(customer.getEmail());
        currentCustomer.setPhone(customer.getPhone());
        currentCustomer.setAddress(customer.getAddress());
        repository.save(currentCustomer);
        return currentCustomer;
    }
}
