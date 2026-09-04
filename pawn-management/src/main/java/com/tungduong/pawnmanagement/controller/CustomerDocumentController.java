package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.dto.request.CustomerDocumentRequest;
import com.tungduong.pawnmanagement.dto.request.filter.CustomerDocumentFilterRequest;
import com.tungduong.pawnmanagement.dto.request.update.CustomerDocumentUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.CustomerDocumentResponse;
import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.helper.PageResponse;
import com.tungduong.pawnmanagement.service.CustomerDocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor

public class CustomerDocumentController {
    private final CustomerDocumentService customerDocumentService;

    @GetMapping("/customer-documents/{id}")
    public ResponseEntity<ApiResponse<CustomerDocumentResponse>> findById(@PathVariable Long id) {
        return ApiResponse.success(customerDocumentService.findById(id));
    }

    @GetMapping("/customer-documents")
    public ResponseEntity<ApiResponse<PageResponse<CustomerDocumentResponse>>> findAll(Pageable pageable, CustomerDocumentFilterRequest request) {
        return ApiResponse.success(PageResponse.from(customerDocumentService.findAll(request, pageable)));
    }

    @PostMapping("/customer-documents")
    public ResponseEntity<ApiResponse<CustomerDocumentResponse>> upload(CustomerDocumentRequest request) throws IOException {
        log.info("controller contentType={}", request.getFile().getContentType());
        return ApiResponse.created(customerDocumentService.upload(request));
    }

    @PutMapping("/customer-documents/{id}")
    public ResponseEntity<ApiResponse<CustomerDocumentResponse>> replaceFile(CustomerDocumentUpdateRequest request,@PathVariable Long id) throws IOException {
        return ApiResponse.success(customerDocumentService.replaceFile(id, request));
    }

    @DeleteMapping("/customer-documents/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id)  {
        customerDocumentService.delete(id);
        return ApiResponse.delete();
    }

    @PatchMapping("/customer-documents/{id}")
    public ResponseEntity<ApiResponse<CustomerDocumentResponse>> update(@PathVariable Long id, @Valid @RequestBody RecordStatusUpdateRequest request) {
        return ApiResponse.success(customerDocumentService.updateRecordStatus(id, request));
    }

    @GetMapping("/customer-documents/{id}/download")
    public ResponseEntity<ApiResponse<Resource>> download(@PathVariable Long id) {
        return ApiResponse.success(customerDocumentService.download(id));
    }

}
