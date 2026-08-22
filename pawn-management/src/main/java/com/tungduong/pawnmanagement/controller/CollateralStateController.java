package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.dto.request.CollateralAppraiseRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralResponse;
import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.service.CollateralStateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collaterals/{id}")
public class CollateralStateController {
    private final CollateralStateService collateralStateService;

    @PostMapping("/appraise")
    public ResponseEntity<ApiResponse<CollateralResponse>> appraisedCollateral(@Valid @RequestBody CollateralAppraiseRequest request,@PathVariable Long id) {
        return ApiResponse.success(collateralStateService.appraised(id,request));
    }
}
