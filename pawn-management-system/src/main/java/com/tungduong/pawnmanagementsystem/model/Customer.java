package com.tungduong.pawnmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name không được để trống")
    @Size(min = 2, max = 30, message = "Name phải từ 2 đến 30 ký tự")
    private String name;

    @NotBlank(message = "CitizenId không được để trống")
    @Pattern(regexp = "^(00[1-9]|0[1-8][0-9]|09[0-6])\\d{9}$", message = "CitizenId phải đủ 12 ký tự")
    private String citizenId;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email phải đúng định dạng")
    private String email;

    @NotBlank(message = "Phone không được để trống")
    @Pattern(regexp = "^(03|05|07|09)\\d{8}$")
    private String phone;

    @NotBlank(message = "Address không được để trống")
    @Size(min = 5, max = 255, message = "Địa chỉ phải từ 5 đến 255 ký tự")
    private String address;

    public Customer(Long id, String name,String citizenId,String email,  String phone, String address) {
        this.address = address;
        this.email = email;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.citizenId = citizenId;
    }
    public Customer(){}
}

