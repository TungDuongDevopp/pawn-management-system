package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.dto.request.filter.RoleFilterRequest;
import com.tungduong.pawnmanagement.model.Role;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpecification {

    public static Specification<Role> recordStatusNot(RecordStatus status) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (status == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.notEqual(root.get("recordStatus"), status);
        };
    }

    public static Specification<Role> hasName(RoleFilterRequest roleFilterRequest) {
        return (root, criteriaQuery, criteriaBuilder) -> {

            if(roleFilterRequest == null ||roleFilterRequest.getName() == null || roleFilterRequest.getName().isBlank()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("name")),roleFilterRequest.getName().trim().toLowerCase());
        };
    }
}
