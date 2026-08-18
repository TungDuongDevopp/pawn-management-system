package com.tungduong.pawnmanagement.model;

import com.tungduong.pawnmanagement.model.base.BaseEntity;
import com.tungduong.pawnmanagement.model.enums.Department;
import com.tungduong.pawnmanagement.model.enums.Position;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "staffs")
@Getter
@Setter
@NoArgsConstructor
public class Staff extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "fullname can not be null")
    private String fullname;

    @NotBlank(message = "phone can not be null")
    private String phone;

    private String email;

    private String address;

    @NotNull(message = "salary can not be null")
    @Positive(message = "salary must greater than 0")
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "department can not be null")
    private Department department;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "position can not be null")
    private Position position;

    @OneToOne
    @JoinColumn(name = "account_id", unique = true)
    private Account account;

    @OneToMany(mappedBy = "staff")
    private List<Contract> contracts;

    @OneToMany(mappedBy = "appraisedBy")
    private List<Collateral> collaterals;
}
