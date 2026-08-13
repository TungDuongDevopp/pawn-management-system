package com.tungduong.pawnmanagement.dto.request;

import com.tungduong.pawnmanagement.model.Role;
import com.tungduong.pawnmanagement.model.enums.AccountStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    private Long id;

    @NotBlank(message = "username can not be null")
    private String username;

    @NotBlank(message = "password can not be null")
    private String password;

    @NotNull(message = "role can not be null")
    private RoleRequest role;

    private AccountStatus status;
}
