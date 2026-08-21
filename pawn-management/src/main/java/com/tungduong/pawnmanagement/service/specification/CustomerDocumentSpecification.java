package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.dto.request.filter.CustomerDocumentFilterRequest;
import com.tungduong.pawnmanagement.model.CustomerDocument;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

public class CustomerDocumentSpecification {

    public static Specification<CustomerDocument> hasCustomerId(CustomerDocumentFilterRequest request) {
        return (root, query,criteriaBuilder)->{
            if(request == null || request.getCustomerId()==null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("customer").get("id"),request.getCustomerId());
        };

    }
    public static Specification<CustomerDocument> hasContentType(CustomerDocumentFilterRequest request) {
        return (root,query,criteriaBuilder)->{
            if(request == null || request.getContentType()==null|| request.getContentType().isEmpty()){
                return criteriaBuilder.conjunction();
            }
            String contentType = "%" +request.getContentType().trim().toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("contentType")),contentType);

        };

    }
    public  static Specification<CustomerDocument> hasExtension(CustomerDocumentFilterRequest request) {
        return (root,query,criteriaBuilder)->{
            if(request == null || request.getExtension()==null|| request.getExtension().isEmpty()){
                return criteriaBuilder.conjunction();
            }
            String extension = "%" + request.getExtension().trim().toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("extension")),extension);

        };

    }
    public static Specification<CustomerDocument> hasFileSize(CustomerDocumentFilterRequest request) {
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
    public static Specification<CustomerDocument> hasDocumentType(CustomerDocumentFilterRequest request) {
        return (root,query,criteriaBuilder)->{
            if( request == null || request.getContentType() == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("contentType")),request.getContentType().trim().toLowerCase());
        };

    }
    public static Specification<CustomerDocument> recordStatusNot(RecordStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.notEqual(root.get("recordStatus"), status);
        };
    }
}
