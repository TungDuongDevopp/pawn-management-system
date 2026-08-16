package com.tungduong.pawnmanagement.mapper;

import com.tungduong.pawnmanagement.dto.request.AssetCategoryRequest;
import com.tungduong.pawnmanagement.dto.response.AssetCategoryResponse;
import com.tungduong.pawnmanagement.model.AssetCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssetCategoryMapper {
    AssetCategory  toEntity(AssetCategoryRequest request);
    AssetCategoryResponse toResponse(AssetCategory entity);
    List<AssetCategoryResponse> toResponseList(List<AssetCategory> entity);
}
