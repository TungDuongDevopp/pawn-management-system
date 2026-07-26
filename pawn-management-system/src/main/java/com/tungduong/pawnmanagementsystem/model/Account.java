package com.tungduong.pawnmanagementsystem.model;

import com.tungduong.pawnmanagementsystem.model.enums.AccountStatus;
import com.tungduong.pawnmanagementsystem.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&!_])[A-Za-z\\d@#$%^&!_]{6,20}$",
            message = "Password phải có 6-20 ký tự, gồm chữ hoa, chữ thường và số")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Vui lòng chọn role")
    private Role role;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Vui lòng chọn status")
    private AccountStatus status;

    public Account(Long id, String username, String password, Role role,AccountStatus status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public Account(){}
}
