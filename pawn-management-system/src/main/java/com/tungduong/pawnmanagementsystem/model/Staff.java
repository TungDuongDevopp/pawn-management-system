package com.tungduong.pawnmanagementsystem.model;

import com.tungduong.pawnmanagementsystem.model.enums.Department;
import com.tungduong.pawnmanagementsystem.model.enums.StaffStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@Entity
@Table(name="staffs")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Vui lòng chọn tài khoản")
    private Account account;

    @NotBlank(message = "Name không được để trống")
    @Size(min = 2, max = 30, message = "Name phải từ 2 đến 30 ký tự")
    private String fullname;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email phải đúng định dạng")
    private String email;

    @NotBlank(message = "Phone không được để trống")
    @Pattern(regexp = "^(03|05|07|09)\\d{8}$")
    private String phone;

    @NotBlank(message = "Address không được để trống")
    @Size(min = 5, max = 255, message = "Địa chỉ phải từ 5 đến 255 ký tự")
    private String address;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Vui lòng chọn deparment")
    private Department deparment;

    @NotBlank(message = "Position không được để trống")
    @Size(min = 5, max = 100, message = "Địa chỉ phải từ 5 đến 100 ký tự")
    private String position;

    @Past(message = "Ngày tuyển phải trong quá khứ")
    @NotNull(message = "Ngày tuyển không được trống")
    private Date hireDate;

    @Min(value = 1000000,message = "Lương không được dưới 1tr")
    private double salary;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Vui lòng chọn status")
    private StaffStatus satus;


}
