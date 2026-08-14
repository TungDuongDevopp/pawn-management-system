package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.AccountFilterRequest;
import com.tungduong.pawnmanagement.dto.request.AccountRequest;
import com.tungduong.pawnmanagement.dto.request.AccountUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.AccountResponse;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.AccountMapper;
import com.tungduong.pawnmanagement.model.Account;
import com.tungduong.pawnmanagement.model.Role;
import com.tungduong.pawnmanagement.model.enums.AccountStatus;
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


    public Page<AccountResponse> findAll(Pageable pageable, AccountFilterRequest filterRequest) {
        Specification<Account> specification = Specification.allOf(
                AccountSpecification.statusNot(AccountStatus.DELETED),
                AccountSpecification.hasRole(filterRequest),
                AccountSpecification.hasStatus(filterRequest),
                AccountSpecification.hasUsername(filterRequest)
        );
        return accountRepository.findAll(specification, pageable)
                .map(accountMapper::toDto);
    }
    public AccountResponse findById(Long id) {
        return accountMapper.toDto(accountRepository.findByIdAndStatusNot(id,AccountStatus.DELETED).orElseThrow(()->new ResourceNotFoundException("Account not found")));
    }

    public AccountResponse create(AccountRequest accountRequest) {
        if (accountRepository.existsByUsernameAndStatusNot(accountRequest.getUsername(), AccountStatus.DELETED)) {
            throw new DuplicateResourceException("Account already exists");
        }

        Long id = accountRequest.getRole().getId();
        String roleName = accountRequest.getRole().getName();
        Role role = roleRepository.findByIdOrName(id, roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        Account account = accountMapper.toEntity(accountRequest);


        account.setStatus(AccountStatus.ACTIVE);
        account.setRole(role);

        return accountMapper.toDto(accountRepository.save(account));
    }

    @Transactional
    public AccountResponse update(AccountUpdateRequest request, Long id) {
        Account currentAccount = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (request.getRole() != null) {
            Role role = roleRepository.findById(request.getRole().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

            currentAccount.setRole(role);
        }

        if (request.getStatus() != null) {
            currentAccount.setStatus(request.getStatus());
        }

        return accountMapper.toDto(currentAccount);
    }

    @Transactional
    public void deleteById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        account.setStatus(AccountStatus.DELETED);
    }

}
