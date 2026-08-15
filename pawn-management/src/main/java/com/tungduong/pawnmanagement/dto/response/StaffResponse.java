package com.tungduong.pawnmanagement.dto.response;


import com.tungduong.pawnmanagement.model.enums.Department;
import com.tungduong.pawnmanagement.model.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponse {

    private Long id;

    private String fullname;

    private String phone;

    private String email;

    private String address;

    private BigDecimal salary;

    private Department department;

    private Position position;
}
