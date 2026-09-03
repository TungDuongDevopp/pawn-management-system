package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.dto.request.filter.CollateralDocumentFilterRequest;
import com.tungduong.pawnmanagement.model.CollateralDocument;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

public class CollateralDocumentSpecification {
    public static Specification<CollateralDocument> hasCollateralId(CollateralDocumentFilterRequest request) {
        return (root, query,criteriaBuilder)->{
            if(request == null || request.getCollateralId()==null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("collateral").get("id"),request.getCollateralId());
        };

    }
    public static Specification<CollateralDocument> hasCollateralTypeId(CollateralDocumentFilterRequest request) {
        return (root, query,criteriaBuilder)->{
            if(request == null || request.getCollateralTypeId()==null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("documentType").get("id"),request.getCollateralTypeId());
        };

    }
    public static Specification<CollateralDocument> hasContentType(CollateralDocumentFilterRequest request) {
        return CommonSpecification.likeIgnoreCase("contentType", request == null ? null : request.getContentType());
    }

    public static Specification<CollateralDocument> hasExtension(CollateralDocumentFilterRequest request) {
        return CommonSpecification.likeIgnoreCase("extension", request == null ? null : request.getExtension());
    }

    public static Specification<CollateralDocument> hasFileSize(CollateralDocumentFilterRequest request) {
        return CommonSpecification.inRange(
                "fileSize",
                request == null ? null : request.getMinFileSize(),
                request == null ? null : request.getMaxFileSize()
        );
    }

    public static Specification<CollateralDocument> recordStatusNot(RecordStatus status) {
        return CommonSpecification.recordStatusNot(status);
    }
}
