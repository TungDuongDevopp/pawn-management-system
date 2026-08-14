package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.dto.request.CustomerFilterRequest;
import com.tungduong.pawnmanagement.dto.request.CustomerRequest;
import com.tungduong.pawnmanagement.dto.request.CustomerRequestUpdate;
import com.tungduong.pawnmanagement.dto.response.CustomerResponse;
import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/customers/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(@PathVariable Long id) {
        return ApiResponse.success(customerService.getById(id));
    }

    @GetMapping("/customers")
    public ResponseEntity<ApiResponse<Page<CustomerResponse>>> getCustomers(Pageable pageable, CustomerFilterRequest customerFilterRequest) {
        return ApiResponse.success(customerService.getAll(pageable, customerFilterRequest));
    }

    @PostMapping("/customers")
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        return ApiResponse.created(customerService.create(customerRequest));
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(@Valid @RequestBody CustomerRequestUpdate customerRequest, @PathVariable Long id) {
        return ApiResponse.success(customerService.update(customerRequest, id));
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
       return ApiResponse.delete("Deleted Successfully");
    }


}
