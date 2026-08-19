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

    @GetMapping("/asset-types")
    public ResponseEntity<ApiResponse<PageResponse<AssetTypeResponse>>> getAssetCategories(Pageable pageable) {
        return ApiResponse.success(PageResponse.from(assetTypeService.getAll(pageable)));
    }

    @GetMapping("/asset-types/{id}")
    public ResponseEntity<ApiResponse<AssetTypeResponse>> getAssetCategoriesById(@PathVariable Long id) {
        return ApiResponse.success(assetTypeService.getById(id));
    }

    @PostMapping("/asset-types")
    public ResponseEntity<ApiResponse<AssetTypeResponse>> createAssetCategories(@RequestBody AssetTypeRequest assetCategoryRequest) {
        return ApiResponse.created(assetTypeService.create(assetCategoryRequest));
    }

    @PutMapping("/asset-types/{id}")
    public ResponseEntity<ApiResponse<AssetTypeResponse>> updateAssetCategories(@RequestBody AssetTypeUpdateRequest assetCategoryRequest, @PathVariable Long id) {
        return ApiResponse.success(assetTypeService.update(assetCategoryRequest,id));
    }

    @PatchMapping("/asset-types/{id}/status")
    public ResponseEntity<ApiResponse<AssetTypeResponse>> updateAssetTypeStatus(@PathVariable Long id, @Valid @RequestBody RecordStatusUpdateRequest request) {
        return ApiResponse.success(assetTypeService.updateRecordStatus(id, request));
    }

    @DeleteMapping("/asset-types/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAssetCategories(@PathVariable Long id) {
        assetTypeService.deleteById(id);
        return ApiResponse.delete();
    }
}
