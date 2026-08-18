package com.tungduong.pawnmanagement.dto.response;

import com.tungduong.pawnmanagement.model.enums.AccountStatus;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
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

    private Long roleId;

    private AccountStatus status;

    private RecordStatus recordStatus;
}
