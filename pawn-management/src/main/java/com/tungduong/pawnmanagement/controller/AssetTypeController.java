package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.dto.request.AssetTypeRequest;
import com.tungduong.pawnmanagement.dto.request.update.AssetTypeUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.AssetTypeResponse;
import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.helper.PageResponse;
import com.tungduong.pawnmanagement.service.AssetTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AssetTypeController {
    private final AssetTypeService assetTypeService;

    @PostMapping("/asset-types")
    public ResponseEntity<ApiResponse<AssetTypeResponse>> create(@Valid @RequestBody AssetTypeRequest assetTypeRequest) {
        return ApiResponse.created(assetTypeService.create(assetTypeRequest));
    }

    @GetMapping("/asset-types")
    public ResponseEntity<ApiResponse<PageResponse<AssetTypeResponse>>> findAll(Pageable pageable) {
        return ApiResponse.success(PageResponse.from(assetTypeService.findAll(pageable)));
    }

    @GetMapping("/asset-types/{id}")
    public ResponseEntity<ApiResponse<AssetTypeResponse>> findById(@PathVariable Long id) {
        return ApiResponse.success(assetTypeService.findById(id));
    }

    @PutMapping("/asset-types/{id}")
    public ResponseEntity<ApiResponse<AssetTypeResponse>> update(@Valid @RequestBody AssetTypeUpdateRequest assetCategoryRequest, @PathVariable Long id) {
        return ApiResponse.success(assetTypeService.update(assetCategoryRequest, id));
    }

    @PatchMapping("/asset-types/{id}")
    public ResponseEntity<ApiResponse<AssetTypeResponse>> update(@PathVariable Long id, @Valid @RequestBody RecordStatusUpdateRequest request) {
        return ApiResponse.success(assetTypeService.updateRecordStatus(id, request));
    }

    @DeleteMapping("/asset-types/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        assetTypeService.delete(id);
        return ApiResponse.delete();
    }
}
