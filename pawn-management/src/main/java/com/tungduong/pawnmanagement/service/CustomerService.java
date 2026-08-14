package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.CustomerFilterRequest;
import com.tungduong.pawnmanagement.dto.request.CustomerRequest;
import com.tungduong.pawnmanagement.dto.request.CustomerRequestUpdate;
import com.tungduong.pawnmanagement.dto.response.CustomerResponse;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.CustomerMapper;

import com.tungduong.pawnmanagement.model.Customer;
import com.tungduong.pawnmanagement.repository.CustomerRepository;
import com.tungduong.pawnmanagement.service.specification.CustomerSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public Page<CustomerResponse> getAll(Pageable pageable, CustomerFilterRequest request) {
        Specification<Customer> specification = Specification.allOf(
                CustomerSpecification.hasEmail(request),
                CustomerSpecification.hasName(request),
                CustomerSpecification.hasPhone(request),
                CustomerSpecification.hasAddress(request)
        );
        return customerRepository.findAll(specification,pageable).map(customerMapper::toDto);
    }

    public CustomerResponse getById(Long id) {
        return customerMapper.toDto(customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found")));
    }

    public CustomerResponse create(CustomerRequest customerRequest) {
        if(customerRepository.existsByPhone(customerRequest.getPhone())) {
            throw new DuplicateResourceException("Phone number already exists");
        }
        if(customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        return customerMapper.toDto(customerRepository.save(customerMapper.toEntity(customerRequest)));
    }

    @Transactional
    public CustomerResponse update(CustomerRequestUpdate customerRequestUpdate, Long id) {
        Customer currentCustomer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));


        if(customerRequestUpdate.getPhone() != null || currentCustomer.getPhone().isBlank()) {
            if(customerRepository.existsByPhone(customerRequestUpdate.getPhone()) && !id.equals(currentCustomer.getId())) {
                throw new DuplicateResourceException("Phone number already exists");
            }
            currentCustomer.setPhone(customerRequestUpdate.getPhone());
        }

        if(customerRequestUpdate.getEmail() != null || currentCustomer.getEmail().isBlank()){
            if(customerRepository.existsByEmail(customerRequestUpdate.getEmail()) && !id.equals(currentCustomer.getId())) {
                throw new DuplicateResourceException("Email already exists");

            }
            currentCustomer.setEmail(customerRequestUpdate.getEmail());
        }

        if(customerRequestUpdate.getFullname() != null || currentCustomer.getFullname().isBlank()){
            currentCustomer.setFullname(customerRequestUpdate.getFullname());
        }

      if(customerRequestUpdate.getAddress() != null || currentCustomer.getAddress().isBlank()){
          currentCustomer.setAddress(customerRequestUpdate.getAddress());
      }


        return customerMapper.toDto(currentCustomer);


    }

    public void deleteById(Long id) {
        if(!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found");
        }
        customerRepository.deleteById(id);
    }
}
