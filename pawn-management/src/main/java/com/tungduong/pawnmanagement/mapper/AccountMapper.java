package com.tungduong.pawnmanagement.mapper;

import com.tungduong.pawnmanagement.dto.request.AccountRequest;
import com.tungduong.pawnmanagement.dto.response.AccountResponse;
import com.tungduong.pawnmanagement.model.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = RoleMapper.class)
public interface AccountMapper {

    Account toEntity(AccountRequest accountRequest);

    AccountResponse toDto(Account account);

    List<AccountResponse> toAccountList(List<Account> accounts);

}
