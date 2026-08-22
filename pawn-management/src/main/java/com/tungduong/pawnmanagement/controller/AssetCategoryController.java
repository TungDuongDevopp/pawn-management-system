package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.dto.request.AssetCategoryRequest;
import com.tungduong.pawnmanagement.dto.request.update.AssetCategoryUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.AssetCategoryResponse;
import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.helper.PageResponse;
import com.tungduong.pawnmanagement.service.AssetCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AssetCategoryController {
    private final AssetCategoryService assetCategoryService;

    @PostMapping("/asset-categories")
    public ResponseEntity<ApiResponse<AssetCategoryResponse>> create(@Valid @RequestBody AssetCategoryRequest assetCategoryRequest) {
        return ApiResponse.created(assetCategoryService.create(assetCategoryRequest));
    }

    @GetMapping("/asset-categories")
    public ResponseEntity<ApiResponse<PageResponse<AssetCategoryResponse>>> findAll(Pageable pageable) {
        return ApiResponse.success(PageResponse.from(assetCategoryService.findAll(pageable)));
    }

    @GetMapping("/asset-categories/{id}")
    public ResponseEntity<ApiResponse<AssetCategoryResponse>> findById(@PathVariable Long id) {
        return ApiResponse.success(assetCategoryService.findById(id));
    }

    @PutMapping("/asset-categories/{id}")
    public ResponseEntity<ApiResponse<AssetCategoryResponse>> update(@Valid @RequestBody AssetCategoryUpdateRequest assetCategoryRequest, @PathVariable Long id) {
        return ApiResponse.success(assetCategoryService.update(assetCategoryRequest, id));
    }

    @PatchMapping("/asset-categories/{id}")
    public ResponseEntity<ApiResponse<AssetCategoryResponse>> update(@PathVariable Long id, @Valid @RequestBody RecordStatusUpdateRequest request) {
        return ApiResponse.success(assetCategoryService.updateRecordStatus(id, request));
    }

    @DeleteMapping("/asset-categories/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        assetCategoryService.delete(id);
        return ApiResponse.delete();
    }
}
