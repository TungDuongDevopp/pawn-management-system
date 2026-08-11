package com.tungduong.pawnmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "fullname can not be null")
    private String fullname;

    @NotBlank(message = "phone can not be null")
    private String phone;

    private String email;

    @NotBlank(message = "address can not be null")
    private String address;

    @OneToOne
    @JoinColumn(name = "account_id", unique = true)
    private Account account;

    @OneToMany(mappedBy = "customer")
    private List<CustomerDocument> documents;

    @OneToMany(mappedBy = "customer")
    private List<Contract> contracts;
}
