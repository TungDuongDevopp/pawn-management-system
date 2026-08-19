package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.filter.AccountFilterRequest;
import com.tungduong.pawnmanagement.dto.request.AccountRequest;
import com.tungduong.pawnmanagement.dto.request.update.AccountUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.AccountResponse;
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

    private void ensureManipulable(Account account, Role role) {
        if (account != null &&
                (account.getRecordStatus() == RecordStatus.DELETED
                        || account.getRecordStatus() == RecordStatus.INACTIVE
                        || account.getStatus() == AccountStatus.DELETED
                        || account.getStatus() == AccountStatus.DISABLED)) {
            throw new CanNotManipulateDataException(
                    "Account cannot be manipulated in its current status"
            );
        }
        if (role != null &&
                (role.getRecordStatus() == RecordStatus.DELETED
                        || role.getRecordStatus() == RecordStatus.INACTIVE)) {
            throw new CanNotManipulateDataException(
                    "Role cannot be manipulated in its current status"
            );
        }
    }

    public Page<AccountResponse> findAll(Pageable pageable, AccountFilterRequest filterRequest) {
        Specification<Account> specification = Specification.allOf(
                AccountSpecification.statusNot(AccountStatus.DELETED),
                AccountSpecification.hasRole(filterRequest),
                AccountSpecification.hasStatus(filterRequest),
                AccountSpecification.hasUsername(filterRequest)
        );
        return accountRepository.findAll(specification, pageable)
                .map(accountMapper::toResponse);
    }

    public AccountResponse findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found with id " + id));
        ensureManipulable(account, null);
        return accountMapper.toResponse(account);
    }

    public AccountResponse create(AccountRequest accountRequest) {
        if (accountRepository.existsByUsernameAndStatusNot(
                accountRequest.getUsername(),
                AccountStatus.DELETED
        )) {
            throw new DuplicateResourceException("Account already exists");
        }
        Long id = accountRequest.getRole().getId();
        String roleName = accountRequest.getRole().getName();
        Role role = roleRepository.findByIdOrName(id, roleName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id " + id));
        ensureManipulable(null, role);
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
        }
        ensureManipulable(currentAccount, role);
        if (role != null) {
            currentAccount.setRole(role);
        }
        if (request.getStatus() != null) {
            currentAccount.setStatus(request.getStatus());
        }
        return accountMapper.toResponse(currentAccount);
    }

    @Transactional
    public void deleteById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found with id " + id));
        ensureManipulable(account, null);
        account.setStatus(AccountStatus.DELETED);
        account.setRecordStatus(RecordStatus.DELETED);
    }
}