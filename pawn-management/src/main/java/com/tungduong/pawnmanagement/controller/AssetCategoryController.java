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

    @GetMapping("/asset-categories")
    public ResponseEntity<ApiResponse<PageResponse<AssetCategoryResponse>>> getAssetCategories(Pageable pageable) {
        return ApiResponse.success(PageResponse.from(assetCategoryService.getAll(pageable)));
    }

    @GetMapping("/asset-categories/{id}")
    public ResponseEntity<ApiResponse<AssetCategoryResponse>> getAssetCategoriesById(@PathVariable Long id) {
        return ApiResponse.success(assetCategoryService.getById(id));
    }

    @PostMapping("/asset-categories")
    public ResponseEntity<ApiResponse<AssetCategoryResponse>> createAssetCategories(@RequestBody AssetCategoryRequest assetCategoryRequest) {
        return ApiResponse.created(assetCategoryService.create(assetCategoryRequest));
    }

    @PutMapping("/asset-categories/{id}")
    public ResponseEntity<ApiResponse<AssetCategoryResponse>> updateAssetCategories(@RequestBody AssetCategoryUpdateRequest assetCategoryRequest,@PathVariable Long id) {
        return ApiResponse.success(assetCategoryService.update(assetCategoryRequest,id));
    }

    @PatchMapping("/asset-categories/{id}/status")
    public ResponseEntity<ApiResponse<AssetCategoryResponse>> updateAssetCategoryStatus(@PathVariable Long id, @Valid @RequestBody RecordStatusUpdateRequest request) {
        return ApiResponse.success(assetCategoryService.updateRecordStatus(id, request));
    }

    @DeleteMapping("/asset-categories/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAssetCategories(@PathVariable Long id) {
        assetCategoryService.deleteById(id);
        return ApiResponse.delete();
    }
}
