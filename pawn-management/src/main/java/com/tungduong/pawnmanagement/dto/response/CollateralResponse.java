package com.tungduong.pawnmanagement.dto.response;

import com.tungduong.pawnmanagement.model.enums.AssetStatus;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollateralResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal declaredValue;

    private BigDecimal appraisedValue;

    private Long customerId;

    private Long assetTypeId;

    private AssetStatus status;

    private Long appraisedByStaffId;

    private Instant appraisedAt;

    private RecordStatus recordStatus;
}
