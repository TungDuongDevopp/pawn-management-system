package com.tungduong.pawnmanagementsystem.model;

import com.tungduong.pawnmanagementsystem.model.enums.AccountStatus;
import com.tungduong.pawnmanagementsystem.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = "password")
@Setter @Getter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username không được để trống")
    @Size(min = 4, max = 20, message = "Username phải từ 4 đến 20 ký tự")
    private String username;

    @Column(length = 60)
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Vui lòng chọn role")
    private Role role;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Vui lòng chọn status")
    private AccountStatus status;

    public Account(){}
}
