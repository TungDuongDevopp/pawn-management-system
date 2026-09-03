package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.dto.request.filter.CollateralImageFilterRequest;
import com.tungduong.pawnmanagement.model.CollateralImage;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

public class CollateralImageSpecification {
    public static Specification<CollateralImage> hasCollateralId(CollateralImageFilterRequest request) {
        return (root, query,criteriaBuilder)->{
            if(request == null || request.getCollateralId()==null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("collateral").get("id"),request.getCollateralId());
        };

    }
    public static Specification<CollateralImage> hasContentType(CollateralImageFilterRequest request) {
        return CommonSpecification.likeIgnoreCase("contentType", request == null ? null : request.getContentType());
    }

    public static Specification<CollateralImage> hasExtension(CollateralImageFilterRequest request) {
        return CommonSpecification.likeIgnoreCase("extension", request == null ? null : request.getExtension());
    }

    public static Specification<CollateralImage> hasFileSize(CollateralImageFilterRequest request) {
        return CommonSpecification.inRange(
                "fileSize",
                request == null ? null : request.getMinFileSize(),
                request == null ? null : request.getMaxFileSize()
        );
    }

    public static Specification<CollateralImage> recordStatusNot(RecordStatus status) {
        return CommonSpecification.recordStatusNot(status);
    }
}
