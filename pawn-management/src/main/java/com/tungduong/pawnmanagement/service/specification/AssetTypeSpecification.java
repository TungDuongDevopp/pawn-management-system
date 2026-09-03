package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.model.AssetType;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

public class AssetTypeSpecification {

    /**
     * Excludes records whose status matches the given RecordStatus.
     * Typically used to hide DELETED records from list queries.
     */
    public static Specification<AssetType> recordStatusNot(RecordStatus status) {
        return CommonSpecification.recordStatusNot(status);
    }

    @Deprecated
    public static Specification<AssetType> statusNot(RecordStatus status) {
        return recordStatusNot(status);
    }
}
