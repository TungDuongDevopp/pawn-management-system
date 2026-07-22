package com.tungduong.pawnmanagementsystem.model;

import com.tungduong.pawnmanagementsystem.model.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = "password")
public class Account {

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String username;

    @Setter
    private String password;
    @Getter @Setter
    private Role role;

    public Account(Long id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
