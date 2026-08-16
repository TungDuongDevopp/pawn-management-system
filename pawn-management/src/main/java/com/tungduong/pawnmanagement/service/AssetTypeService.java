package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.AssetTypeRequest;
import com.tungduong.pawnmanagement.dto.request.update.AssetTypeUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.AssetTypeResponse;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.AssetTypeMapper;
import com.tungduong.pawnmanagement.model.AssetCategory;
import com.tungduong.pawnmanagement.model.AssetType;
import com.tungduong.pawnmanagement.model.enums.CategoryStatus;
import com.tungduong.pawnmanagement.repository.AssetCategoryRepository;
import com.tungduong.pawnmanagement.repository.AssetTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetTypeService {
    private final AssetTypeRepository assetTypeRepository;
    private final AssetCategoryRepository assetCategoryRepository;
    private final AssetTypeMapper assetTypeMapper;

    public Page<AssetTypeResponse> getAll(Pageable pageable) {
        return assetTypeRepository.findAll(pageable).map(assetTypeMapper::toResponse);
    }

    public AssetTypeResponse getById(Long id) {
        return assetTypeMapper.toResponse(assetTypeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Asset Category Not Found")));
    }

    public AssetTypeResponse create(AssetTypeRequest request) {
        if(assetTypeRepository.existsByName(request.getName())){
            throw new DuplicateResourceException("Asset Category Name Already Exists");
        }
        AssetCategory assetCategory = assetCategoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Asset Category Not Found"));

        AssetType assetType = assetTypeMapper.toEntity(request);
        assetType.setCategory(assetCategory);
        assetType.setStatus(CategoryStatus.ACTIVE);
        return assetTypeMapper.toResponse(assetTypeRepository.save(assetType));
    }

    @Transactional
    public AssetTypeResponse update(AssetTypeUpdateRequest request, Long id) {

        AssetType assetType = assetTypeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Asset Category Not Found"));
        if(request.getName() != null && !request.getName().isBlank()){
            if(assetTypeRepository.existsByName(request.getName()) && !id.equals(assetType .getId())){
                throw new DuplicateResourceException("Asset Category Name Already Exists");
            }
            assetType .setName(request.getName());

        }
        if(request.getDescription() != null && !request.getDescription().isBlank()){
            assetType .setDescription(request.getDescription());
        }
        if(request.getCategoryId() != null){
            AssetCategory assetCategory = assetCategoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Asset Category Not Found"));
            assetType.setCategory(assetCategory);
        }
        return assetTypeMapper.toResponse(assetType);
    }

    @Transactional
    public void deleteById(Long id) {
        AssetType assetType = assetTypeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Asset Type Not Found"));
        assetType.setStatus(CategoryStatus.INACTIVE);
    }


}
