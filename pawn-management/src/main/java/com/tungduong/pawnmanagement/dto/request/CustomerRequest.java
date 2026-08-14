package com.tungduong.pawnmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    private Long id;

    @NotBlank(message = "fullname can not be null")
    private String fullname;

    @NotBlank(message = "phone can not be null")
    private String phone;

    private String email;

    @NotBlank(message = "address can not be null")
    private String address;
}
