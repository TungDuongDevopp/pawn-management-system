package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.dto.request.filter.CollateralFilterRequest;
import com.tungduong.pawnmanagement.model.Collateral;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

public class CollateralSpecification {

    public static Specification<Collateral> recordStatusNot(RecordStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.notEqual(root.get("recordStatus"), status);
        };
    }

    public static Specification<Collateral> hasName(CollateralFilterRequest request) {
        return (root, query, criteriaBuilder) -> {
            if (request == null || request.getName() == null || request.getName().isBlank()) {
                return criteriaBuilder.conjunction();
            }
            String name = "%" + request.getName().trim().toLowerCase() + "%";
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    name
            );
        };
    }

    public static Specification<Collateral> hasDeclaredValue(CollateralFilterRequest request) {
        return (root, query, criteriaBuilder) -> {
            if (request == null) {
                return criteriaBuilder.conjunction();
            }

            if (request.getMinDeclaredValue() != null && request.getMaxDeclaredValue() != null) {
                return criteriaBuilder.between(
                        root.get("declaredValue"),
                        request.getMinDeclaredValue(),
                        request.getMaxDeclaredValue()
                );
            }

            if (request.getMinDeclaredValue() != null) {
                return criteriaBuilder.greaterThanOrEqualTo(
                        root.get("declaredValue"),
                        request.getMinDeclaredValue()
                );
            }

            if (request.getMaxDeclaredValue() != null) {
                return criteriaBuilder.lessThanOrEqualTo(
                        root.get("declaredValue"),
                        request.getMaxDeclaredValue()
                );
            }

            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Collateral> hasAppraisedValue(CollateralFilterRequest request) {
        return (root, query, criteriaBuilder) -> {
            if (request == null) {
                return criteriaBuilder.conjunction();
            }

            if (request.getMinAppraisedValue() != null && request.getMaxAppraisedValue() != null) {
                return criteriaBuilder.between(
                        root.get("appraisedValue"),
                        request.getMinAppraisedValue(),
                        request.getMaxAppraisedValue()
                );
            }

            if (request.getMinAppraisedValue() != null) {
                return criteriaBuilder.greaterThanOrEqualTo(
                        root.get("appraisedValue"),
                        request.getMinAppraisedValue()
                );
            }

            if (request.getMaxAppraisedValue() != null) {
                return criteriaBuilder.lessThanOrEqualTo(
                        root.get("appraisedValue"),
                        request.getMaxAppraisedValue()
                );
            }

            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Collateral> hasCustomerId(CollateralFilterRequest request) {
        return (root, query, criteriaBuilder) -> {
            if (request == null || request.getCustomerId() == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("customer").get("id"), request.getCustomerId());
        };
    }

    public static Specification<Collateral> hasAssetTypeId(CollateralFilterRequest request) {
        return (root, query, criteriaBuilder) -> {
            if (request == null || request.getAssetTypeId() == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("type").get("id"), request.getAssetTypeId());
        };
    }

    public static Specification<Collateral> hasStatus(CollateralFilterRequest request) {
        return (root, query, criteriaBuilder) -> {
            if (request == null || request.getStatus() == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), request.getStatus());
        };
    }

    public static Specification<Collateral> hasAppraisedByStaffId(CollateralFilterRequest request) {
        return (root, query, criteriaBuilder) -> {
            if (request == null || request.getAppraisedByStaffId() == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("appraisedBy").get("id"), request.getAppraisedByStaffId());
        };
    }

    public static Specification<Collateral> hasAppraisedAt(CollateralFilterRequest request) {
        return (root, query, criteriaBuilder) -> {
            if (request == null) {
                return criteriaBuilder.conjunction();
            }

            if (request.getAppraisedAtFrom() != null && request.getAppraisedAtTo() != null) {
                return criteriaBuilder.between(
                        root.get("appraisedAt"),
                        request.getAppraisedAtFrom(),
                        request.getAppraisedAtTo()
                );
            }

            if (request.getAppraisedAtFrom() != null) {
                return criteriaBuilder.greaterThanOrEqualTo(
                        root.get("appraisedAt"),
                        request.getAppraisedAtFrom()
                );
            }

            if (request.getAppraisedAtTo() != null) {
                return criteriaBuilder.lessThanOrEqualTo(
                        root.get("appraisedAt"),
                        request.getAppraisedAtTo()
                );
            }

            return criteriaBuilder.conjunction();
        };
    }
}
