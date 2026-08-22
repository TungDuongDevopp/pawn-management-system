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

    @PostMapping("/collateral-document-types")
    public ResponseEntity<ApiResponse<CollateralDocumentTypeResponse>> create(@RequestBody @Valid CollateralDocumentTypeRequest collateralDocumentTypeRequest) {
        return ApiResponse.created(collateralDocumentTypeService.create(collateralDocumentTypeRequest));
    }

    @GetMapping("/collateral-document-types")
    public ResponseEntity<ApiResponse<PageResponse<CollateralDocumentTypeResponse>>> findAll(Pageable pageable) {
        return ApiResponse.success(PageResponse.from(collateralDocumentTypeService.findAll(pageable)));
    }

    @GetMapping("/collateral-document-types/{id}")
    public ResponseEntity<ApiResponse<CollateralDocumentTypeResponse>> findById(@PathVariable Long id) {
        return ApiResponse.success(collateralDocumentTypeService.findById(id));
    }

    @PutMapping("/collateral-document-types/{id}")
    public ResponseEntity<ApiResponse<CollateralDocumentTypeResponse>> update(@Valid @RequestBody CollateralDocumentTypeUpdateRequest collateralDocumentTypeRequest, @PathVariable Long id) {
        return ApiResponse.success(collateralDocumentTypeService.update(collateralDocumentTypeRequest, id));
    }

    @PatchMapping("/collateral-document-types/{id}")
    public ResponseEntity<ApiResponse<CollateralDocumentTypeResponse>> update(@PathVariable Long id, @Valid @RequestBody RecordStatusUpdateRequest request) {
        return ApiResponse.success(collateralDocumentTypeService.updateRecordStatus(id, request));
    }

    @DeleteMapping("/collateral-document-types/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        collateralDocumentTypeService.delete(id);
        return ApiResponse.delete();
    }
}
