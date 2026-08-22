package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.dto.request.filter.StaffFilterRequest;
import com.tungduong.pawnmanagement.dto.request.StaffRequest;
import com.tungduong.pawnmanagement.dto.request.update.StaffUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.StaffResponse;
import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.helper.PageResponse;
import com.tungduong.pawnmanagement.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;

    @PostMapping("/staffs")
    public ResponseEntity<ApiResponse<StaffResponse>> create(@Valid @RequestBody StaffRequest staffRequest) {
        return ApiResponse.created(staffService.create(staffRequest));
    }

    @GetMapping("/staffs")
    public ResponseEntity<ApiResponse<PageResponse<StaffResponse>>> findAll(Pageable pageable, StaffFilterRequest request) {
        return ApiResponse.success(PageResponse.from(staffService.findAll(pageable, request)));
    }

    @GetMapping("/staffs/{id}")
    public ResponseEntity<ApiResponse<StaffResponse>> findById(@PathVariable Long id) {
        return ApiResponse.success(staffService.findById(id));
    }

    @PutMapping("/staffs/{id}")
    public ResponseEntity<ApiResponse<StaffResponse>> update(@Valid @RequestBody StaffUpdateRequest staffRequest, @PathVariable Long id) {
        return ApiResponse.success(staffService.update(staffRequest, id));
    }

    @PatchMapping("/staffs/{id}")
    public ResponseEntity<ApiResponse<StaffResponse>> update(@PathVariable Long id, @Valid @RequestBody RecordStatusUpdateRequest request) {
        return ApiResponse.success(staffService.updateRecordStatus(id, request));
    }

    @DeleteMapping("/staffs/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        staffService.delete(id);
        return ApiResponse.delete();
    }

}
