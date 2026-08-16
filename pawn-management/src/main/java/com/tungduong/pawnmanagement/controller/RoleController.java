package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.dto.request.RoleFilterRequest;
import com.tungduong.pawnmanagement.dto.request.RoleRequest;
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
    private ResponseEntity<ApiResponse<RoleResponse>>createRole(@Valid @RequestBody RoleRequest roleInput) {
        return ApiResponse.created(roleService.save(roleInput));
    }

    @GetMapping("/roles")
    private ResponseEntity<ApiResponse<List<RoleResponse>>>getRoles(RoleFilterRequest roleFilterRequest) {
        return ApiResponse.success(roleService.findAll(roleFilterRequest));
    }

    @GetMapping("/roles/{id}")
    private ResponseEntity<ApiResponse<RoleResponse>> getRoleById(@PathVariable Long id) {
        return ApiResponse.success(roleService.findById(id));
    }

    @PutMapping("/roles/{id}")
    private ResponseEntity<ApiResponse<RoleResponse>> updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequest roleInput) {
        return ApiResponse.success(roleService.update(id, roleInput));
    }

    @DeleteMapping("/roles/{id}")
    private ResponseEntity<ApiResponse<Void>> deleteRoleById(@PathVariable Long id) {
        roleService.deleteById(id);
        return ApiResponse.delete();
    }
}
