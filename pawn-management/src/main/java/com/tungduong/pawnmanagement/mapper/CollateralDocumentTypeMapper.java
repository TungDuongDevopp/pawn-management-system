package com.tungduong.pawnmanagement.mapper;

import com.tungduong.pawnmanagement.dto.request.CollateralDocumentTypeRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralDocumentTypeResponse;
import com.tungduong.pawnmanagement.model.CollateralDocumentType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CollateralDocumentTypeMapper {
    CollateralDocumentType toEntity(CollateralDocumentTypeRequest collateralDocumentTypeRequest);
    CollateralDocumentTypeResponse toResponse(CollateralDocumentType collateralDocumentType);
}
