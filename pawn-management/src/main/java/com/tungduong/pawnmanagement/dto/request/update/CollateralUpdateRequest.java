package com.tungduong.pawnmanagement.dto.request.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollateralUpdateRequest {
    private Long id;

    private String name;

    private String description;

    private BigDecimal declaredValue;

    private Long customerId;

    private Long assetTypeId;
}
