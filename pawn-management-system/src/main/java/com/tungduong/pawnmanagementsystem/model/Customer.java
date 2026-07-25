package com.tungduong.pawnmanagementsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;

    public Customer(Long id, String name,String email,  String phone, String address) {
        this.address = address;
        this.email = email;
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
    public Customer(){}
}

