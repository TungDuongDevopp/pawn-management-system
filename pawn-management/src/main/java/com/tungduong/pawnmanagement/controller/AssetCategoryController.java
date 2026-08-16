package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.dto.request.AssetCategoryRequest;
import com.tungduong.pawnmanagement.dto.request.update.AssetCategoryUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.AssetCategoryResponse;
import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.service.AssetCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AssetCategoryController {
    private final AssetCategoryService assetCategoryService;

    @GetMapping("/asset-categories")
    public ResponseEntity<ApiResponse<List<AssetCategoryResponse>>> getAssetCategories() {
        return ApiResponse.success(assetCategoryService.getAll());
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

    @DeleteMapping("/asset-categories/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAssetCategories(@PathVariable Long id) {
        assetCategoryService.deleteById(id);
        return ApiResponse.delete();
    }
}
