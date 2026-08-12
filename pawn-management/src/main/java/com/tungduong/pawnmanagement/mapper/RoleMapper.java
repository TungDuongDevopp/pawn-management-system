package com.tungduong.pawnmanagement.mapper;

import com.tungduong.pawnmanagement.dto.request.RoleRequest;
import com.tungduong.pawnmanagement.dto.response.RoleResponse;
import com.tungduong.pawnmanagement.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleRequest request);

    RoleResponse toResponse(Role role);
    List<RoleResponse> toResponseList(List<Role> roles);
}
