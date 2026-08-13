package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.dto.request.RoleFilterRequest;
import com.tungduong.pawnmanagement.model.Role;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpecification {
    public static Specification<Role> hasName(RoleFilterRequest roleFilterRequest) {
        return (root, criteriaQuery, criteriaBuilder) -> {

            if(roleFilterRequest.getName() == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("name")),roleFilterRequest.getName());
        };
    }
}
