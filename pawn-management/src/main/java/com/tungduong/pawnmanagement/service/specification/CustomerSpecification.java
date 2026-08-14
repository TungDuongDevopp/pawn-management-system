package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.dto.request.CustomerFilterRequest;
import com.tungduong.pawnmanagement.model.Customer;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

    public static Specification<Customer> hasName(CustomerFilterRequest request) {

        return (root,query,criteriaBuilder)->{
            if(request == null || request.getFullname() == null || request.getFullname().isBlank())
                return criteriaBuilder.conjunction();

            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("fullname")),request.getFullname().trim().toLowerCase());
        };
    }

    public static Specification<Customer> hasEmail(CustomerFilterRequest request) {

        return (root,query,criteriaBuilder)->{
            if(request == null || request.getEmail() == null || request.getEmail().isBlank())
                return criteriaBuilder.conjunction();

            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("email")),request.getEmail().trim().toLowerCase());
        };
    }

    public static Specification<Customer> hasAddress(CustomerFilterRequest request) {

        return (root,query,criteriaBuilder)->{
            if(request == null || request.getAddress() == null || request.getAddress().isBlank())
                return criteriaBuilder.conjunction();

            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("address")),request.getAddress().trim().toLowerCase());
        };
    }

    public static Specification<Customer> hasPhone(CustomerFilterRequest request) {

        return (root,query,criteriaBuilder)->{
            if(request == null || request.getPhone() == null || request.getPhone().isBlank())
                return criteriaBuilder.conjunction();

            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("phone")),request.getPhone().trim().toLowerCase());
        };
    }
}
