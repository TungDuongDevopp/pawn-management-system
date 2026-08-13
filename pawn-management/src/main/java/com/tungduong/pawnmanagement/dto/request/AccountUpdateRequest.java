package com.tungduong.pawnmanagement.dto.request;

import com.tungduong.pawnmanagement.model.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateRequest {
    private RoleRequest role;
    private AccountStatus status;
}
