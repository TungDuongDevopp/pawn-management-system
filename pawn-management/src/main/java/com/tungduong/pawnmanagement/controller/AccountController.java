package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.dto.request.filter.AccountFilterRequest;
import com.tungduong.pawnmanagement.dto.request.AccountRequest;
import com.tungduong.pawnmanagement.dto.request.update.AccountUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.AccountResponse;
import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.helper.PageResponse;
import com.tungduong.pawnmanagement.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<ApiResponse<AccountResponse>> create(@Valid @RequestBody AccountRequest accountRequest) {
        return ApiResponse.created(accountService.create(accountRequest));
    }

    @GetMapping("/accounts")
    public ResponseEntity<ApiResponse<PageResponse<AccountResponse>>> findAll(Pageable pageable, AccountFilterRequest filterRequest) {
        return ApiResponse.success(PageResponse.from(accountService.findAll(pageable, filterRequest)));
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> findById(@PathVariable Long id) {
        return ApiResponse.success(accountService.findById(id));
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> update(@Valid @RequestBody AccountUpdateRequest accountRequest, @PathVariable Long id) {
        return ApiResponse.success(accountService.update(accountRequest, id));
    }

    @PatchMapping("/accounts/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> update(@PathVariable Long id, @Valid @RequestBody RecordStatusUpdateRequest request) {
        return ApiResponse.success(accountService.updateRecordStatus(id, request));
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ApiResponse.delete();
    }

}
