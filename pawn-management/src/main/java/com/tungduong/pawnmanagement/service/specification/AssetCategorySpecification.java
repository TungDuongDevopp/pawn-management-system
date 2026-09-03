package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.model.AssetCategory;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

public class AssetCategorySpecification {

    /**
     * Excludes records whose status matches the given RecordStatus.
     * Typically used to hide DELETED records from list queries.
     */
    public static Specification<AssetCategory> recordStatusNot(RecordStatus status) {
        return CommonSpecification.recordStatusNot(status);
    }

    @Deprecated
    public static Specification<AssetCategory> statusNot(RecordStatus status) {
        return recordStatusNot(status);
    }
}
