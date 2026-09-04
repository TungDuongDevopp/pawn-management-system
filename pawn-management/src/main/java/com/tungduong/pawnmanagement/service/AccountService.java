package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.filter.AccountFilterRequest;
import com.tungduong.pawnmanagement.dto.request.AccountRequest;
import com.tungduong.pawnmanagement.dto.request.update.AccountUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.AccountResponse;
import com.tungduong.pawnmanagement.helper.EntityGuard;
import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.AccountMapper;
import com.tungduong.pawnmanagement.model.Account;
import com.tungduong.pawnmanagement.model.Role;
import com.tungduong.pawnmanagement.model.enums.AccountStatus;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import com.tungduong.pawnmanagement.repository.AccountRepository;
import com.tungduong.pawnmanagement.repository.RoleRepository;
import com.tungduong.pawnmanagement.service.specification.AccountSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final RoleRepository roleRepository;

    private void ensureManipulable(Account account) {
        if (account != null) {
            EntityGuard.requireManipulable(account, "Account");
            if (account.getStatus() == AccountStatus.DELETED
                    || account.getStatus() == AccountStatus.DISABLED) {
                throw new CanNotManipulateDataException(
                        "Account cannot be manipulated in its current status"
                );
            }
        }
    }

    public Page<AccountResponse> findAll(Pageable pageable, AccountFilterRequest filterRequest) {
        Specification<Account> specification = Specification.allOf(
                AccountSpecification.statusNot(AccountStatus.DELETED),
                AccountSpecification.recordStatusNot(RecordStatus.DELETED),
                AccountSpecification.hasRole(filterRequest),
                AccountSpecification.hasStatus(filterRequest),
                AccountSpecification.hasUsername(filterRequest)
        );
        return accountRepository.findAll(specification, pageable)
                .map(accountMapper::toResponse);
    }

    public AccountResponse findById(Long id) {
        Account account = accountRepository.findByIdAndRecordStatusNotAndStatusNot(id, RecordStatus.DELETED, AccountStatus.DELETED)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found with id " + id));
        return accountMapper.toResponse(account);
    }

    @Transactional
    public AccountResponse create(AccountRequest accountRequest) {
        if (accountRepository.existsByUsernameAndStatusNot(
                accountRequest.getUsername(),
                AccountStatus.DELETED
        )) {
            throw new DuplicateResourceException("Account already exists");
        }
        Role role = roleRepository.findByIdAndRecordStatusNot(accountRequest.getRoleId(), RecordStatus.DELETED)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id " + accountRequest.getRoleId()));
        EntityGuard.requireAssignable(role, "Role");
        Account account = accountMapper.toEntity(accountRequest);
        account.setStatus(AccountStatus.ACTIVE);
        account.setRole(role);
        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Transactional
    public AccountResponse update(AccountUpdateRequest request, Long id) {
        Account currentAccount = accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found with id " + id));

        Role role = null;
        if (request.getRole() != null) {
            role = roleRepository.findById(request.getRole().getId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Role not found with id " + request.getRole().getId()));
            EntityGuard.requireAssignable(role, "Role");
        }
        ensureManipulable(currentAccount);
        if (role != null) {
            currentAccount.setRole(role);
        }
        if (request.getStatus() != null) {
            if(request.getStatus() == AccountStatus.DELETED){
                throw new CanNotManipulateDataException("Account cannot be deleted via status. Use delete API instead");
            }
            currentAccount.setStatus(request.getStatus());
        }
        return accountMapper.toResponse(currentAccount);
    }

    @Transactional
    public AccountResponse updateRecordStatus(Long id, RecordStatusUpdateRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found with id " + id));

        EntityGuard.requireNotDeleted(account, "Account");

        if (request.getRecordStatus() == RecordStatus.DELETED) {
            account.setStatus(AccountStatus.DELETED);
        }
        account.setRecordStatus(request.getRecordStatus());
        return accountMapper.toResponse(account);
    }

    @Transactional
    public void delete(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found with id " + id));
        ensureManipulable(account);
        account.setStatus(AccountStatus.DELETED);
        account.setRecordStatus(RecordStatus.DELETED);
    }
}