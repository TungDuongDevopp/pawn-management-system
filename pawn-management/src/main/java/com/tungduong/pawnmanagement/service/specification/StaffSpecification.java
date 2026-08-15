package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.dto.request.StaffFilterRequest;
import com.tungduong.pawnmanagement.model.Staff;
import org.springframework.data.jpa.domain.Specification;

public class StaffSpecification {
    public static Specification<Staff> hasFullName(StaffFilterRequest request) {
        return (root, query, criteriaBuilder) -> {

            if (request == null
                    || request.getFullname() == null
                    || request.getFullname().isBlank()) {
                return criteriaBuilder.conjunction();
            }

            String fullname = "%" + request.getFullname().trim().toLowerCase() + "%";

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("fullname")),
                    fullname
            );
        };
    }

    public static Specification<Staff> hasEmail(StaffFilterRequest request){
        return (root,query,criteriaBuilder)->{
            if(request == null || request.getEmail() == null || request.getEmail().isBlank()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("email")),request.getEmail().trim().toLowerCase());
        };
    }
    public static Specification<Staff> hasPhone(StaffFilterRequest request){
        return (root,query,criteriaBuilder)->{
            if(request == null || request.getPhone() == null || request.getPhone().isBlank()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("phone")),request.getPhone().trim().toLowerCase());
        };
    }

    public static Specification<Staff> hasAddress(StaffFilterRequest request){
        return (root,query,criteriaBuilder)->{
            if(request == null || request.getAddress() == null || request.getAddress().isBlank()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("address")),request.getAddress().trim().toLowerCase());
        };
    }

    public static Specification<Staff> hasSalary(StaffFilterRequest request) {
        return (root, query, criteriaBuilder) -> {

            if (request == null) {
                return criteriaBuilder.conjunction();
            }

            if (request.getMinSalary() != null && request.getMaxSalary() != null) {
                return criteriaBuilder.between(
                        root.get("salary"),
                        request.getMinSalary(),
                        request.getMaxSalary()
                );
            }

            if (request.getMinSalary() != null) {
                return criteriaBuilder.greaterThanOrEqualTo(
                        root.get("salary"),
                        request.getMinSalary()
                );
            }

            if (request.getMaxSalary() != null) {
                return criteriaBuilder.lessThanOrEqualTo(
                        root.get("salary"),
                        request.getMaxSalary()
                );
            }

            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Staff> hasPosition(StaffFilterRequest request){
        return (root,query,criteriaBuilder)->{
            if(request == null || request.getPosition() == null || request.getPosition().isBlank()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("position")),request.getPosition().trim().toLowerCase());
        };
    }

    public static Specification<Staff> hasDepartment(StaffFilterRequest request){
        return (root,query,criteriaBuilder)->{
            if(request == null || request.getDepartment() == null || request.getDepartment().isBlank()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("department")),request.getDepartment().trim().toLowerCase());
        };
    }
}
