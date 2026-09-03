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
        return CommonSpecification.likeIgnoreCase("contentType", request == null ? null : request.getContentType());
    }

    public static Specification<CustomerDocument> hasExtension(CustomerDocumentFilterRequest request) {
        return CommonSpecification.likeIgnoreCase("extension", request == null ? null : request.getExtension());
    }

    public static Specification<CustomerDocument> hasFileSize(CustomerDocumentFilterRequest request) {
        return CommonSpecification.inRange(
                "fileSize",
                request == null ? null : request.getMinFileSize(),
                request == null ? null : request.getMaxFileSize()
        );
    }

    public static Specification<CustomerDocument> hasDocumentType(CustomerDocumentFilterRequest request) {
        return (root,query,criteriaBuilder)->{
            if( request == null || request.getCustomerDocumentType()== null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("customerDocumentType")),request.getCustomerDocumentType().name().toLowerCase());
        };

    }
    public static Specification<CustomerDocument> recordStatusNot(RecordStatus status) {
        return CommonSpecification.recordStatusNot(status);
    }
}
