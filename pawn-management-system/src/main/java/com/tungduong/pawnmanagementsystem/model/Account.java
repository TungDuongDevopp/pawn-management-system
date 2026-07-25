package com.tungduong.pawnmanagementsystem.model;

import com.tungduong.pawnmanagementsystem.model.enums.AccountStatus;
import com.tungduong.pawnmanagementsystem.model.enums.Role;
import jakarta.persistence.*;
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
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    AccountStatus status;

    public Account(Long id, String username, String password, Role role,AccountStatus status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public Account(){}
}
