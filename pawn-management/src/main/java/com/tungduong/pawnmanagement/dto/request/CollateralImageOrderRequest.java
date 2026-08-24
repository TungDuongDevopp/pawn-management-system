package com.tungduong.pawnmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollateralImageOrderRequest {
    @NotNull(message = "displayOrder can not be null")
    private Integer displayOrder;
}
