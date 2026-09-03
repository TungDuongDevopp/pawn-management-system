package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.dto.request.filter.CustomerFilterRequest;
import com.tungduong.pawnmanagement.model.Customer;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

    public static Specification<Customer> recordStatusNot(RecordStatus status) {
        return CommonSpecification.recordStatusNot(status);
    }

    public static Specification<Customer> hasName(CustomerFilterRequest request) {
        return CommonSpecification.likeIgnoreCase("fullname", request == null ? null : request.getFullname());
    }

    public static Specification<Customer> hasEmail(CustomerFilterRequest request) {
        return CommonSpecification.equalIgnoreCase("email", request == null ? null : request.getEmail());
    }

    public static Specification<Customer> hasAddress(CustomerFilterRequest request) {
        return CommonSpecification.equalIgnoreCase("address", request == null ? null : request.getAddress());
    }

    public static Specification<Customer> hasPhone(CustomerFilterRequest request) {
        return CommonSpecification.equalIgnoreCase("phone", request == null ? null : request.getPhone());
    }
}

