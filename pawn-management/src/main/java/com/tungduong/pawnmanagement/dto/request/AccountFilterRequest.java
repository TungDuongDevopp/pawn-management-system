package com.tungduong.pawnmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountFilterRequest {

    @NotBlank( message = "Status can not be null")
    private String status;

    @NotBlank( message = "Username can not be null")
    private String username;

    @NotNull(message = "role can not be null")
    private String roleName;
}
