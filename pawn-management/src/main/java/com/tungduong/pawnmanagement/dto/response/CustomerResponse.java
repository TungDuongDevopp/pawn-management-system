package com.tungduong.pawnmanagement.dto.response;

import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Long id;

    private String fullname;

    private String phone;

    private String email;

    private String address;

    private RecordStatus recordStatus;
}
