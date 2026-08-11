package com.tungduong.pawnmanagement.model;

import com.tungduong.pawnmanagement.model.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "username can not be null")
    private String username;

    @NotBlank(message = "password can not be null")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "account status can not be null")
    AccountStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    Role role;

    @OneToMany(mappedBy = "account")
    List<Feedback> feedbacks;

    Instant createdAt;

    Instant updatedAt;

}
