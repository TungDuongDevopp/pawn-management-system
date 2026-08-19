package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.model.CollateralDocumentType;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

public class CollateralDocumentTypeSpecification {
    public static Specification<CollateralDocumentType> statusNot(RecordStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.notEqual(root.get("recordStatus"), status);
        };
    }
}
