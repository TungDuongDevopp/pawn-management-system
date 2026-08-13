package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.dto.request.AccountRequest;
import com.tungduong.pawnmanagement.dto.request.AccountUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.AccountResponse;
import com.tungduong.pawnmanagement.helper.ApiResponse;
import com.tungduong.pawnmanagement.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@Valid @RequestBody AccountRequest accountRequest) {
        return ApiResponse.created(accountService.create(accountRequest));
    }

    @GetMapping("/accounts")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAccounts() {
        return ApiResponse.success(accountService.findAll());
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable Long id) {
        return ApiResponse.success(accountService.findById(id));
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccount(@Valid @RequestBody AccountUpdateRequest accountRequest, @PathVariable Long id) {
        return ApiResponse.success(accountService.update(accountRequest,id));
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccountById(@PathVariable Long id) {
        accountService.deleteById(id);
        return ApiResponse.delete("Deleted successfully");
    }

}
