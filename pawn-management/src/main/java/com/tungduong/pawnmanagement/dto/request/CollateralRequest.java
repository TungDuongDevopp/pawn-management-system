package com.tungduong.pawnmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollateralRequest {

    private Long id;

    @NotBlank(message = "name can not be null")
    private String name;

    private String description;

    @NotNull(message = "declared value can not be null")
    private BigDecimal declaredValue;

    @NotNull(message = "CustomerId can not be null")
    private Long customerId;

    @NotNull(message = "CustomerId can not be null")
    private Long assetTypeId;
}
