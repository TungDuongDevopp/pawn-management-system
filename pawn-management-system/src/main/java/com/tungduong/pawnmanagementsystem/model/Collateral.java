package com.tungduong.pawnmanagementsystem.model;

import com.tungduong.pawnmanagementsystem.model.enums.CollateralSatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor
@Entity
@Table(name = "collaterals")
@Getter @Setter
public class Collateral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name không được để trống")
    @Size(min = 4 ,message = "name phải lớn hơn 4 ký tự")
    private String name;

    @NotBlank(message = "Vui lòng nhập URL ảnh")
    @URL(message = "URL ảnh không hợp lệ")
    private String imageUrl;

    private String description;

    @NotBlank(message = "Giá không được để trống")
    private double valuation;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Vui lòng chọn status")
    private CollateralSatus status;

}
