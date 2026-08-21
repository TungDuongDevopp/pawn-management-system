package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.service.interfaces.IFileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final IFileStorageService fileStorageService;

    @PostMapping("/files")
    public ResponseEntity<ApiResponse<String>> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String folder = "FileTest";
        return ApiResponse.created(fileStorageService.save(file,folder));
    }

    @GetMapping("/files/{storageKey}")
    public ResponseEntity<ApiResponse<Resource>> download(@PathVariable String storageKey) {
        return ApiResponse.success(fileStorageService.get(storageKey));
    }

    @DeleteMapping("/files/{storageKey}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String storageKey) {
        fileStorageService.delete(storageKey);
        return ApiResponse.delete();
    }
}
