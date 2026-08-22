package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.dto.request.filter.CollateralFilterRequest;
import com.tungduong.pawnmanagement.dto.request.CollateralRequest;
import com.tungduong.pawnmanagement.dto.request.update.CollateralUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralResponse;
import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.helper.PageResponse;
import com.tungduong.pawnmanagement.service.CollateralService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CollateralController {
    private final CollateralService collateralService;

    @PostMapping("/collaterals")
    public ResponseEntity<ApiResponse<CollateralResponse>> create(@Valid @RequestBody CollateralRequest request){
        return ApiResponse.created(collateralService.create(request));
    }

    @GetMapping("/collaterals")
    public ResponseEntity<ApiResponse<PageResponse<CollateralResponse>>> findAll(Pageable pageable, CollateralFilterRequest request) {
        return ApiResponse.success(PageResponse.from(collateralService.findAll(pageable, request)));
    }

    @GetMapping("/collaterals/{id}")
    public ResponseEntity<ApiResponse<CollateralResponse>> findById(@PathVariable Long id) {
        return ApiResponse.success(collateralService.findById(id));
    }

    @PutMapping("/collaterals/{id}")
    public ResponseEntity<ApiResponse<CollateralResponse>> update(@Valid @RequestBody CollateralUpdateRequest request, @PathVariable Long id) {
        return ApiResponse.success(collateralService.update(request, id));
    }

    @PatchMapping("/collaterals/{id}")
    public ResponseEntity<ApiResponse<CollateralResponse>> update(@PathVariable Long id, @Valid @RequestBody RecordStatusUpdateRequest request) {
        return ApiResponse.success(collateralService.updateRecordStatus(id, request));
    }

    @DeleteMapping("/collaterals/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        collateralService.delete(id);
        return ApiResponse.delete();
    }

}
