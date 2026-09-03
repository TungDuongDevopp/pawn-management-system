package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.dto.request.filter.CollateralFilterRequest;
import com.tungduong.pawnmanagement.model.Collateral;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

public class CollateralSpecification {

    public static Specification<Collateral> recordStatusNot(RecordStatus status) {
        return CommonSpecification.recordStatusNot(status);
    }

    public static Specification<Collateral> hasName(CollateralFilterRequest request) {
        return CommonSpecification.likeIgnoreCase("name", request == null ? null : request.getName());
    }

    public static Specification<Collateral> hasDeclaredValue(CollateralFilterRequest request) {
        return CommonSpecification.inRange(
                "declaredValue",
                request == null ? null : request.getMinDeclaredValue(),
                request == null ? null : request.getMaxDeclaredValue()
        );
    }

    public static Specification<Collateral> hasAppraisedValue(CollateralFilterRequest request) {
        return CommonSpecification.inRange(
                "appraisedValue",
                request == null ? null : request.getMinAppraisedValue(),
                request == null ? null : request.getMaxAppraisedValue()
        );
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
        return CommonSpecification.inRange(
                "appraisedAt",
                request == null ? null : request.getAppraisedAtFrom(),
                request == null ? null : request.getAppraisedAtTo()
        );
    }
}
