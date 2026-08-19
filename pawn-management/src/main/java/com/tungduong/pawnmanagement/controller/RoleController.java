package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.dto.request.filter.RoleFilterRequest;
import com.tungduong.pawnmanagement.dto.request.RoleRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.RoleResponse;
import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/roles")
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@Valid @RequestBody RoleRequest roleInput) {
        return ApiResponse.created(roleService.save(roleInput));
    }

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getRoles(RoleFilterRequest roleFilterRequest) {
        return ApiResponse.success(roleService.findAll(roleFilterRequest));
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(@PathVariable Long id) {
        return ApiResponse.success(roleService.findById(id));
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequest roleInput) {
        return ApiResponse.success(roleService.update(id, roleInput));
    }

    @PatchMapping("/roles/{id}/status")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRoleStatus(@PathVariable Long id, @Valid @RequestBody RecordStatusUpdateRequest request) {
        return ApiResponse.success(roleService.updateRecordStatus(id, request));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRoleById(@PathVariable Long id) {
        roleService.deleteById(id);
        return ApiResponse.delete();
    }
}
