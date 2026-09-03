package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.model.CollateralDocumentType;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

public class CollateralDocumentTypeSpecification {
    public static Specification<CollateralDocumentType> recordStatusNot(RecordStatus status) {
        return CommonSpecification.recordStatusNot(status);
    }

    @Deprecated
    public static Specification<CollateralDocumentType> statusNot(RecordStatus status) {
        return recordStatusNot(status);
    }
}
