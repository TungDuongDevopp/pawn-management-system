package com.tungduong.pawnmanagement.dto.response;

import com.tungduong.pawnmanagement.model.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private Long id;

    private String username;

    private RoleResponse role;

    private AccountStatus status;
}
