package com.tungduong.pawnmanagement.mapper;

import com.tungduong.pawnmanagement.dto.request.AccountRequest;
import com.tungduong.pawnmanagement.dto.response.AccountResponse;
import com.tungduong.pawnmanagement.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",uses = RoleMapper.class)

public interface AccountMapper {

    Account toEntity(AccountRequest accountRequest);

    @Mapping(source = "role.id",target = "roleId")
    AccountResponse toDto(Account account);


}
