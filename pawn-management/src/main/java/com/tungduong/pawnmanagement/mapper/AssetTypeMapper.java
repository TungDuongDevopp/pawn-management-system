package com.tungduong.pawnmanagement.mapper;

import com.tungduong.pawnmanagement.dto.request.AssetTypeRequest;
import com.tungduong.pawnmanagement.dto.response.AssetTypeResponse;
import com.tungduong.pawnmanagement.model.AssetType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssetTypeMapper {
    AssetType toEntity(AssetTypeRequest request);
    AssetTypeResponse toResponse(AssetType entity);
    List<AssetTypeResponse> toResponseList(List<AssetType> entity);
}
