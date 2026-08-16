package com.tungduong.pawnmanagement.dto.request.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CustomerFilterRequest {


    private String fullname;

    private String phone;

    private String email;

    private String address;
}
