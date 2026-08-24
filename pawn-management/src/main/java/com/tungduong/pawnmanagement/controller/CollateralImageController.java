package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.dto.request.CollateralImageRequest;
import com.tungduong.pawnmanagement.dto.request.filter.CollateralImageFilterRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralImageResponse;
import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.helper.PageResponse;
import com.tungduong.pawnmanagement.service.CollateralImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class CollateralImageController {
    private final CollateralImageService collateralImageService;

    @GetMapping("/collaterals/images")
    public ResponseEntity<ApiResponse<PageResponse<CollateralImageResponse>>> findAll(Pageable pageable, CollateralImageFilterRequest request) {
        return ApiResponse.success(PageResponse.from(collateralImageService.findAll(pageable,request)));

    }

    @GetMapping("/collaterals/images/{id}")
    public ResponseEntity<ApiResponse<CollateralImageResponse>> findById(@PathVariable Long id) {
        return ApiResponse.success(collateralImageService.findById(id));
    }
    @PostMapping("/collaterals/images")
    public ResponseEntity<ApiResponse<CollateralImageResponse>> upload(CollateralImageRequest request) throws IOException {
        return ApiResponse.created(collateralImageService.upload(request));
    }

    @DeleteMapping("/collaterals/images/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        collateralImageService.deleteById(id);
        return ApiResponse.delete();
    }
    @PutMapping("/collaterals/images/{id}")
    public ResponseEntity<ApiResponse<CollateralImageResponse>> replaceFile(@PathVariable Long id, MultipartFile file) throws IOException {
        return ApiResponse.success(collateralImageService.replaceFile(id, file));
    }

    @PatchMapping("/collaterals/images/{id}/primary")
    public ResponseEntity<ApiResponse<CollateralImageResponse>> setPrimary(@PathVariable Long id){
        return ApiResponse.success(collateralImageService.setImagePrimary(id));
    }

    @PatchMapping("/collaterals/images/{id}/display-order")
    public ResponseEntity<ApiResponse<CollateralImageResponse>> setDisplayOrder(@PathVariable Long id, @RequestBody Integer displayOrder) {
        return ApiResponse.success(collateralImageService.setImageDisplayOrder(id, displayOrder));
    }

    @PatchMapping("/collaterals/images/{id}")
    public ResponseEntity<ApiResponse<CollateralImageResponse>> update(@PathVariable Long id, @Valid @RequestBody RecordStatusUpdateRequest request) {
        return ApiResponse.success(collateralImageService.updateRecordStatus(id, request));
    }


}
