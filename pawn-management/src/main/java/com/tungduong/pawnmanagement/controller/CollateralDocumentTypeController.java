package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.dto.request.CollateralDocumentTypeRequest;
import com.tungduong.pawnmanagement.dto.request.update.CollateralDocumentTypeUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralDocumentTypeResponse;
import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.helper.PageResponse;
import com.tungduong.pawnmanagement.service.CollateralDocumentTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CollateralDocumentTypeController {
    private final CollateralDocumentTypeService collateralDocumentTypeService;

    @GetMapping("/collateral-document-types")
    public ResponseEntity<ApiResponse<PageResponse<CollateralDocumentTypeResponse>>> getCollateralDocumentTypes(Pageable pageable) {
        return ApiResponse.success(PageResponse.from(collateralDocumentTypeService.getAll(pageable)));
    }

    @GetMapping("/collateral-document-types/{id}")
    public ResponseEntity<ApiResponse<CollateralDocumentTypeResponse>> getCollateralDocumentTypeById(@PathVariable Long id) {
        return ApiResponse.success(collateralDocumentTypeService.getById(id));
    }

    @PostMapping("/collateral-document-types")
    public ResponseEntity<ApiResponse<CollateralDocumentTypeResponse>> createCollateralDocumentType(@RequestBody @Valid CollateralDocumentTypeRequest collateralDocumentTypeRequest) {
        return ApiResponse.created(collateralDocumentTypeService.create(collateralDocumentTypeRequest));
    }

    @PutMapping("/collateral-document-types/{id}")
    public ResponseEntity<ApiResponse<CollateralDocumentTypeResponse>> updateCollateralDocumentType(@Valid @RequestBody CollateralDocumentTypeUpdateRequest collateralDocumentTypeRequest, @PathVariable Long id) {
        return ApiResponse.success(collateralDocumentTypeService.update(collateralDocumentTypeRequest, id));
    }

    @PatchMapping("/collateral-document-types/{id}/status")
    public ResponseEntity<ApiResponse<CollateralDocumentTypeResponse>> updateCollateralDocumentTypeStatus(@PathVariable Long id, @Valid @RequestBody RecordStatusUpdateRequest request) {
        return ApiResponse.success(collateralDocumentTypeService.updateRecordStatus(id, request));
    }

    @DeleteMapping("/collateral-document-types/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCollateralDocumentType(@PathVariable Long id) {
        collateralDocumentTypeService.deleteById(id);
        return ApiResponse.delete();
    }
}
