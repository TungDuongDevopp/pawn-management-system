package com.tungduong.pawnmanagement.mapper;

import com.tungduong.pawnmanagement.dto.request.CollateralImageRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralImageResponse;
import com.tungduong.pawnmanagement.model.CollateralImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CollateralImageMapper {
    CollateralImage toEntity(CollateralImageRequest collateralImageRequest);

    @Mapping(source = "collateral.id" , target = "collateralId")
    CollateralImageResponse toResponse(CollateralImage collateralImage);
}
