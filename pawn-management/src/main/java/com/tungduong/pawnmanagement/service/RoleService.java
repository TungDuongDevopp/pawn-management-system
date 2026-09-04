package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.filter.RoleFilterRequest;
import com.tungduong.pawnmanagement.dto.request.RoleRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.RoleResponse;
import com.tungduong.pawnmanagement.helper.EntityGuard;
import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.RoleMapper;
import com.tungduong.pawnmanagement.model.Role;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import com.tungduong.pawnmanagement.repository.AccountRepository;
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
    private final AccountRepository accountRepository;

    private void ensureManipulable(Role role){
        if (role != null) {
            EntityGuard.requireManipulable(role, "Role");
        }
    }

    public List<RoleResponse> findAll(RoleFilterRequest roleFilterRequest) {
        Specification<Role> specification = Specification.allOf(
                RoleSpecification.recordStatusNot(RecordStatus.DELETED),
                RoleSpecification.hasName(roleFilterRequest)
        );
        return roleMapper.toResponseList(roleRepository.findAll(specification));
    }

    public RoleResponse findById(Long id) {
        Role role = roleRepository.findByIdAndRecordStatusNot(id, RecordStatus.DELETED)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
        return roleMapper.toResponse(role);
    }

    @Transactional
    public RoleResponse create(RoleRequest roleRequest) {

        if(roleRepository.existsByNameAndRecordStatusNot(roleRequest.getName(),RecordStatus.DELETED)){
            throw new DuplicateResourceException("Role already exists");
        }
        Role role = roleMapper.toEntity(roleRequest);
        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Transactional
    public RoleResponse update(Long id, RoleRequest roleRequest) {
        Role currentRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));

        ensureManipulable(currentRole);

        if (roleRepository.existsByNameAndIdNot(roleRequest.getName(), id)) {
            throw new DuplicateResourceException("Role already exists");
        }
        currentRole.setName(roleRequest.getName());
        currentRole.setDescription(roleRequest.getDescription());
        return roleMapper.toResponse(currentRole);
    }

    @Transactional
    public RoleResponse updateRecordStatus(Long id, RecordStatusUpdateRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));

        EntityGuard.requireNotDeleted(role, "Role");

        if (request.getRecordStatus() == RecordStatus.DELETED) {
            if (accountRepository.existsByRoleId(id)) {
                throw new CanNotManipulateDataException("Role is in use and cannot be deleted");
            }
        }

        role.setRecordStatus(request.getRecordStatus());
        return roleMapper.toResponse(role);
    }

    @Transactional
    public void delete(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
        ensureManipulable(role);
        if (accountRepository.existsByRoleId(id)) {
            throw new CanNotManipulateDataException("Role is in use and cannot be deleted");
        }
        role.setRecordStatus(RecordStatus.DELETED);
    }
}

