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
        return (root,query,criteriaBuilder)->{
            if(request == null || request.getContentType()==null|| request.getContentType().isEmpty()){
                return criteriaBuilder.conjunction();
            }
            String contentType = "%" +request.getContentType().trim().toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("contentType")),contentType);

        };

    }
    public  static Specification<CollateralDocument> hasExtension(CollateralDocumentFilterRequest request) {
        return (root,query,criteriaBuilder)->{
            if(request == null || request.getExtension()==null|| request.getExtension().isEmpty()){
                return criteriaBuilder.conjunction();
            }
            String extension = "%" + request.getExtension().trim().toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("extension")),extension);

        };

    }
    public static Specification<CollateralDocument> hasFileSize(CollateralDocumentFilterRequest request) {
        return(root,query,criteriaBuilder)->{

            if(request == null){
                return criteriaBuilder.conjunction();
            }
            if(request.getMinFileSize() != null && request.getMaxFileSize() != null){
                return criteriaBuilder.between(root.get("fileSize"),request.getMinFileSize(),request.getMaxFileSize());
            }
            if(request.getMinFileSize() != null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("fileSize"),request.getMinFileSize());
            }
            if(request.getMaxFileSize() != null){
                return criteriaBuilder.lessThanOrEqualTo(root.get("fileSize"),request.getMaxFileSize());
            }
            return criteriaBuilder.conjunction();
        };
    }


    public static Specification<CollateralDocument> recordStatusNot(RecordStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.notEqual(root.get("recordStatus"), status);
        };
    }
}
