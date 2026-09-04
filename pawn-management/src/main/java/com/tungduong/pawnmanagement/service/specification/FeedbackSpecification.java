package com.tungduong.pawnmanagement.service.specification;


import com.tungduong.pawnmanagement.dto.request.filter.FeedbackFilterRequest;
import com.tungduong.pawnmanagement.model.Feedback;
import com.tungduong.pawnmanagement.model.enums.FeedBackStatus;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

public class FeedbackSpecification {
    public static Specification<Feedback>feedbackStatusNot(FeedBackStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.notEqual(root.get("status"), status);
        };
    }

    public static Specification<Feedback>feedbackStatus(FeedbackFilterRequest request) {
        return (root, query, criteriaBuilder) -> {
            if (request == null || request.getStatus() == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal((root.get("status")), request.getStatus());
        };
    }

    public static Specification<Feedback> recordStatusNot(RecordStatus recordStatus) {
        return (root, query, criteriaBuilder) -> {
            if (recordStatus == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.notEqual(root.get("recordStatus"), recordStatus);
        };
    }
    public static Specification<Feedback> hasAccountId(FeedbackFilterRequest request) {
        return(root,query,criteriaBuilder)->{
            if (request == null || request.getAccountId() == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("account").get("id"), request.getAccountId());

        };
    }
}
