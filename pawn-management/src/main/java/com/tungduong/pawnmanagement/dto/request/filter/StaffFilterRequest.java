package com.tungduong.pawnmanagement.dto.request.filter;

import com.tungduong.pawnmanagement.model.enums.Department;
import com.tungduong.pawnmanagement.model.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffFilterRequest {

    private String fullname;

    private String phone;

    private String email;

    private String address;

    private BigDecimal minSalary;
    private BigDecimal maxSalary;

    private Department department;

    private Position position;
}
