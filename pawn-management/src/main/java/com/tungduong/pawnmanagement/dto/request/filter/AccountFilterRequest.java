package com.tungduong.pawnmanagement.dto.request.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountFilterRequest {

    private String status;

    private String username;

    private String roleName;
}
