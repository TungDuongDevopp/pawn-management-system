package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.RoleFilterRequest;
import com.tungduong.pawnmanagement.dto.request.RoleRequest;
import com.tungduong.pawnmanagement.dto.response.RoleResponse;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.RoleMapper;
import com.tungduong.pawnmanagement.model.Role;
import com.tungduong.pawnmanagement.repository.RoleRepository;
import com.tungduong.pawnmanagement.service.specification.RoleSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public List<RoleResponse> findAll(RoleFilterRequest roleFilterRequest) {
        Specification<Role> specification = Specification.where(RoleSpecification.hasName(roleFilterRequest));
        return roleMapper.toResponseList(roleRepository.findAll(specification));
    }
    public RoleResponse findById(Long id) {
        return roleMapper.toResponse(roleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Role not found")));
    }

    public RoleResponse save(RoleRequest roleRequest) {
        Role role = roleMapper.toEntity(roleRequest);
        if(roleRepository.existsByName(role.getName())) {
            throw new DuplicateResourceException("Role already exists");
        }
        return roleMapper.toResponse(roleRepository.save(role));
    }
    public void deleteById(Long id) {
        if(!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found");
        }
        roleRepository.deleteById(id);
    }
    @Transactional
    public RoleResponse update(Long id, RoleRequest roleRequest) {
        Role role = roleMapper.toEntity(roleRequest);

        if(roleRepository.existsByName(role.getName()) && !id.equals(role.getId())) {
            throw new DuplicateResourceException("Role already exists");
        }
        Role currentRole = roleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Role not found"));
        currentRole.setName(role.getName());
        currentRole.setDescription(role.getDescription());
        return roleMapper.toResponse(currentRole);
    }
}
