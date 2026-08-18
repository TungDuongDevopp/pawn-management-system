package com.tungduong.pawnmanagement.dto.request.filter;

import com.tungduong.pawnmanagement.model.enums.AssetStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollateralFilterRequest {

    private String name;

    private BigDecimal minDeclaredValue;
    private BigDecimal maxDeclaredValue;

    private BigDecimal minAppraisedValue;
    private BigDecimal maxAppraisedValue;

    private Long customerId;

    private Long assetTypeId;

    private AssetStatus status;
    private Long appraisedByStaffId;
    private Instant appraisedAtFrom;
    private Instant appraisedAtTo;
}
