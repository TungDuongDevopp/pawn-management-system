package com.tungduong.pawnmanagement.mapper;

import com.tungduong.pawnmanagement.dto.request.CollateralRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralResponse;
import com.tungduong.pawnmanagement.model.Collateral;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CollateralMapper {

    Collateral toEntity(CollateralRequest collateralRequest);

    @Mapping(source = "customer.id",target = "customerId")
    @Mapping(source = "type.id",target = "assetTypeId")
    @Mapping(source = "appraisedBy.id",target = "appraisedByStaffId")
    CollateralResponse toDto(Collateral collateral);

    List<CollateralResponse> toResponseList(List<Collateral> collaterals);
}
