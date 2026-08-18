package com.tungduong.pawnmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollateralAppraiseRequest {
    private Long id;

    @NotNull(message = "appraisedValue can not be null")
    private BigDecimal appraisedValue;

    @NotNull(message = "Staff id can not be null")
    private Long appraisedBy;


}
