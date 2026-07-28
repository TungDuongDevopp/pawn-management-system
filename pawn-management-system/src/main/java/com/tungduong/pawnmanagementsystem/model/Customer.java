package com.tungduong.pawnmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotNull(message = "Vui lòng chọn tài khoản")
    @OneToOne
    @JoinColumn(name = "account_id",unique = true)
    private Account account;


    public Customer(){}
}

