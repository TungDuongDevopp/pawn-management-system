package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.dto.request.filter.StaffFilterRequest;
import com.tungduong.pawnmanagement.model.Staff;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

public class StaffSpecification {

    public static Specification<Staff> recordStatusNot(RecordStatus status) {
        return CommonSpecification.recordStatusNot(status);
    }

    public static Specification<Staff> hasFullName(StaffFilterRequest request) {
        return CommonSpecification.likeIgnoreCase("fullname", request == null ? null : request.getFullname());
    }

    public static Specification<Staff> hasEmail(StaffFilterRequest request) {
        return CommonSpecification.equalIgnoreCase("email", request == null ? null : request.getEmail());
    }

    public static Specification<Staff> hasPhone(StaffFilterRequest request) {
        return CommonSpecification.equalIgnoreCase("phone", request == null ? null : request.getPhone());
    }

    public static Specification<Staff> hasAddress(StaffFilterRequest request) {
        return CommonSpecification.equalIgnoreCase("address", request == null ? null : request.getAddress());
    }

    public static Specification<Staff> hasSalary(StaffFilterRequest request) {
        return CommonSpecification.inRange(
                "salary",
                request == null ? null : request.getMinSalary(),
                request == null ? null : request.getMaxSalary()
        );
    }

    public static Specification<Staff> hasPosition(StaffFilterRequest request) {
        return (root, query, criteriaBuilder) -> {
            if (request == null || request.getPosition() == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("position"), request.getPosition());
        };
    }

    public static Specification<Staff> hasDepartment(StaffFilterRequest request) {
        return (root, query, criteriaBuilder) -> {
            if (request == null || request.getDepartment() == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("department"), request.getDepartment());
        };
    }
}
