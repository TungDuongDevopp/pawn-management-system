package com.tungduong.pawnmanagement.mapper;

import com.tungduong.pawnmanagement.dto.request.CollateralDocumentRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralDocumentResponse;
import com.tungduong.pawnmanagement.dto.response.CollateralResponse;
import com.tungduong.pawnmanagement.model.CollateralDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CollateralDocumentMapper {

    CollateralDocument toEntity(CollateralDocumentRequest collateralDocumentRequest);

    @Mapping(source = "collateral.id",target = "collateralId")
    @Mapping(source = "documentType.id",target = "collateralTypeId")
    CollateralDocumentResponse toResponse(CollateralDocument collateralDocument);
}
