package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.AccountFilterRequest;
import com.tungduong.pawnmanagement.dto.response.AccountResponse;
import com.tungduong.pawnmanagement.mapper.AccountMapper;
import com.tungduong.pawnmanagement.model.Account;
import com.tungduong.pawnmanagement.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    @Test
    void findAll_ShouldCallRepositoryFindAllWithSpecificationAndPageable() {
        Pageable pageable = PageRequest.of(0, 10);
        AccountFilterRequest filterRequest = new AccountFilterRequest("ACTIVE", "user1", "ADMIN");
        Account account = new Account();
        AccountResponse responseDto = new AccountResponse();

        Page<Account> accountPage = new PageImpl<>(List.of(account));
        when(accountRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(accountPage);
        when(accountMapper.toDto(account)).thenReturn(responseDto);

        Page<AccountResponse> result = accountService.findAll(pageable, filterRequest);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        ArgumentCaptor<Specification<Account>> specCaptor = ArgumentCaptor.forClass(Specification.class);
        verify(accountRepository).findAll(specCaptor.capture(), eq(pageable));
        assertNotNull(specCaptor.getValue());
    }
}
