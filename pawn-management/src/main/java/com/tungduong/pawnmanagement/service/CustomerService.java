package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.filter.CustomerFilterRequest;
import com.tungduong.pawnmanagement.dto.request.CustomerRequest;
import com.tungduong.pawnmanagement.dto.request.update.CustomerUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.CustomerResponse;
import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.CustomerMapper;
import com.tungduong.pawnmanagement.model.Customer;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import com.tungduong.pawnmanagement.repository.CustomerRepository;
import com.tungduong.pawnmanagement.service.specification.CustomerSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    private void ensureManipulable(Customer customer) {
        if (customer.getRecordStatus() == RecordStatus.DELETED
                || customer.getRecordStatus() == RecordStatus.INACTIVE) {
            throw new CanNotManipulateDataException(
                    "Customer cannot be manipulated in its current status"
            );
        }
    }

    public Page<CustomerResponse> getAll(Pageable pageable, CustomerFilterRequest request) {
        Specification<Customer> specification = Specification.allOf(
                CustomerSpecification.recordStatusNot(RecordStatus.DELETED),
                CustomerSpecification.hasEmail(request),
                CustomerSpecification.hasName(request),
                CustomerSpecification.hasPhone(request),
                CustomerSpecification.hasAddress(request)
        );
        return customerRepository.findAll(specification, pageable).map(customerMapper::toResponse);
    }

    public CustomerResponse getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
        ensureManipulable(customer);
        return customerMapper.toResponse(customer);
    }

    public CustomerResponse create(CustomerRequest customerRequest) {
        if (customerRepository.existsByPhoneAndRecordStatusNot(customerRequest.getPhone(), RecordStatus.DELETED)) {
            throw new DuplicateResourceException("Phone number already exists");
        }
        if (customerRequest.getEmail() != null && !customerRequest.getEmail().isBlank()
                && customerRepository.existsByEmailAndRecordStatusNot(customerRequest.getEmail(), RecordStatus.DELETED)) {
            throw new DuplicateResourceException("Email already exists");
        }

        return customerMapper.toResponse(customerRepository.save(customerMapper.toEntity(customerRequest)));
    }

    @Transactional
    public CustomerResponse update(CustomerUpdateRequest customerUpdateRequest, Long id) {
        Customer currentCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));

        ensureManipulable(currentCustomer);

        if (customerUpdateRequest.getPhone() != null && !customerUpdateRequest.getPhone().isBlank()) {
            if (customerRepository.existsByPhoneAndIdNotAndRecordStatusNot(customerUpdateRequest.getPhone(), id, RecordStatus.DELETED)) {
                throw new DuplicateResourceException("Phone number already exists");
            }
            currentCustomer.setPhone(customerUpdateRequest.getPhone());
        }

        if (customerUpdateRequest.getEmail() != null && !customerUpdateRequest.getEmail().isBlank()) {
            if (customerRepository.existsByEmailAndIdNotAndRecordStatusNot(customerUpdateRequest.getEmail(), id, RecordStatus.DELETED)) {
                throw new DuplicateResourceException("Email already exists");
            }
            currentCustomer.setEmail(customerUpdateRequest.getEmail());
        }

        if (customerUpdateRequest.getFullname() != null && !customerUpdateRequest.getFullname().isBlank()) {
            currentCustomer.setFullname(customerUpdateRequest.getFullname());
        }

        if (customerUpdateRequest.getAddress() != null && !customerUpdateRequest.getAddress().isBlank()) {
            currentCustomer.setAddress(customerUpdateRequest.getAddress());
        }

        return customerMapper.toResponse(currentCustomer);
    }

    @Transactional
    public CustomerResponse updateRecordStatus(Long id, RecordStatusUpdateRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));

        if (customer.getRecordStatus() == RecordStatus.DELETED) {
            throw new CanNotManipulateDataException("Customer cannot be manipulated in its current status");
        }

        customer.setRecordStatus(request.getRecordStatus());
        return customerMapper.toResponse(customer);
    }

    @Transactional
    public void deleteById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));

        ensureManipulable(customer);

        customer.setRecordStatus(RecordStatus.DELETED);
    }
}


