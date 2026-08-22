package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.dto.request.CollateralDocumentRequest;
import com.tungduong.pawnmanagement.dto.request.filter.CollateralDocumentFilterRequest;
import com.tungduong.pawnmanagement.dto.request.update.CollateralDocumentUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralDocumentResponse;
import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.helper.PageResponse;
import com.tungduong.pawnmanagement.service.CollateralDocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class CollateralDocumentController {
    private final CollateralDocumentService collateralDocumentService;

    @GetMapping("/collateral-documents")
    public ResponseEntity<ApiResponse<PageResponse<CollateralDocumentResponse>>> findAll(Pageable pageable, CollateralDocumentFilterRequest request) {
        return ApiResponse.success(PageResponse.from(collateralDocumentService.findAll(pageable,request)));
    }

    @GetMapping("/collateral-documents/{id}")
    public ResponseEntity<ApiResponse<CollateralDocumentResponse>> findById(@PathVariable Long id) {
        return ApiResponse.success(collateralDocumentService.findById(id));
    }

    @PostMapping("/collateral-documents")
    public ResponseEntity<ApiResponse<CollateralDocumentResponse>> upload(CollateralDocumentRequest request) throws IOException {
        return ApiResponse.created(collateralDocumentService.upload(request));
    }

    @GetMapping("/collateral-documents/{id}/download")
    public ResponseEntity<ApiResponse<Resource>> download(@PathVariable Long id) {
        return ApiResponse.success(collateralDocumentService.download(id));
    }

    @PutMapping("/collateral-documents/{id}")
    public ResponseEntity<ApiResponse<CollateralDocumentResponse>> replacefile(@PathVariable Long id, CollateralDocumentUpdateRequest request) throws IOException {
       return ApiResponse.success(collateralDocumentService.replaceFile(id,request));
    }

    @DeleteMapping("/collateral-documents/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        collateralDocumentService.delete(id);
        return ApiResponse.delete();
    }

    @PatchMapping("/collateral-documents/{id}")
    public ResponseEntity<ApiResponse<CollateralDocumentResponse>> update(@PathVariable Long id, @Valid @RequestBody RecordStatusUpdateRequest request) {
        return ApiResponse.success(collateralDocumentService.updateRecordStatus(id, request));
    }
}
