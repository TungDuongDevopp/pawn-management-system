package com.tungduong.pawnmanagement.dto.request.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateRequest {

    private Long id;
    private String fullname;

    private String phone;

    private String email;

    private String address;
}
