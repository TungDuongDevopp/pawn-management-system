package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.dto.request.filter.RoleFilterRequest;
import com.tungduong.pawnmanagement.model.Role;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpecification {

    public static Specification<Role> recordStatusNot(RecordStatus status) {
        return CommonSpecification.recordStatusNot(status);
    }

    public static Specification<Role> hasName(RoleFilterRequest roleFilterRequest) {
        return CommonSpecification.equalIgnoreCase("name", roleFilterRequest == null ? null : roleFilterRequest.getName());
    }
}
