package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.AssetCategoryRequest;
import com.tungduong.pawnmanagement.dto.request.update.AssetCategoryUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.AssetCategoryResponse;
import com.tungduong.pawnmanagement.helper.exception.CanDeleteException;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.AssetCategoryMapper;
import com.tungduong.pawnmanagement.model.AssetCategory;
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
public class AssetCategoryService {
    private final AssetCategoryRepository assetCategoryRepository;
    private final AssetCategoryMapper assetCategoryMapper;
    private final AssetTypeRepository assetTypeRepository;

    public Page<AssetCategoryResponse> getAll(Pageable pageable) {
        return assetCategoryRepository.findAll(pageable).map(assetCategoryMapper::toResponse);
    }

    public AssetCategoryResponse getById(Long id) {
        return assetCategoryMapper.toResponse(assetCategoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Asset Category Not Found")));
    }


    public AssetCategoryResponse create(AssetCategoryRequest request) {
        if(assetCategoryRepository.existsByName(request.getName())){
            throw new DuplicateResourceException("Asset Category Name Already Exists");
        }
        AssetCategory assetCategory = assetCategoryMapper.toEntity(request);
        assetCategory.setStatus(CategoryStatus.ACTIVE);
        return assetCategoryMapper.toResponse(assetCategoryRepository.save(assetCategory));
    }

    @Transactional
    public AssetCategoryResponse update(AssetCategoryUpdateRequest request, Long id) {

        AssetCategory assetCategory = assetCategoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Asset Category Not Found"));
        if(request.getName() != null && !request.getName().isBlank()){
            if(assetCategoryRepository.existsByName(request.getName()) && !id.equals(assetCategory.getId())){
                throw new DuplicateResourceException("Asset Category Name Already Exists");
            }
            assetCategory.setName(request.getName());

        }
        if(request.getDescription() != null && !request.getDescription().isBlank()){
            assetCategory.setDescription(request.getDescription());
        }
        if(request.getStatus() != null){
            assetCategory.setStatus(request.getStatus());
        }
        return  assetCategoryMapper.toResponse(assetCategory);
    }

    @Transactional
    public void deleteById(Long id) {
        AssetCategory assetCategory = assetCategoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Asset Category Not Found"));

        if(assetTypeRepository.existsById(id)){
            throw new CanDeleteException("Can Delete Asset Category");
        }
        assetCategory.setStatus(CategoryStatus.INACTIVE);
    }
}

