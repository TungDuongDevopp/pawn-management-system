package com.tungduong.pawnmanagement.controller;
import com.tungduong.pawnmanagement.dto.request.FeedbackRequest;
import com.tungduong.pawnmanagement.dto.request.filter.FeedbackFilterRequest;
import com.tungduong.pawnmanagement.dto.response.FeedBackResponse;
import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.helper.PageResponse;
import com.tungduong.pawnmanagement.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FeedbackController {
    private final FeedbackService feedbackService;

    @GetMapping("/feedbacks")
    public ResponseEntity<ApiResponse<PageResponse<FeedBackResponse>>> findAll(Pageable pageable, FeedbackFilterRequest request) {
        return ApiResponse.success(PageResponse.from(feedbackService.findAll(pageable, request)));
    }

    @GetMapping("/feedbacks/{id}")
    public ResponseEntity<ApiResponse<FeedBackResponse>> findbyId(@PathVariable Long id) {
        return ApiResponse.success(feedbackService.findById(id));
    }

    @PostMapping("/feedbacks")
    public ResponseEntity<ApiResponse<FeedBackResponse>> create(FeedbackRequest request) throws IOException {
        return ApiResponse.created(feedbackService.create(request));
    }

    @GetMapping("feedbacks/attachments/{id}/download")
    public ResponseEntity<ApiResponse<Resource>> download(@PathVariable Long id) {
        return ApiResponse.success(feedbackService.download(id));
    }

}
