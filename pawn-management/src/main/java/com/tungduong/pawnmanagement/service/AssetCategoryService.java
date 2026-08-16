package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.AssetCategoryRequest;
import com.tungduong.pawnmanagement.dto.request.update.AssetCategoryUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.AssetCategoryResponse;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.AssetCategoryMapper;
import com.tungduong.pawnmanagement.model.AssetCategory;
import com.tungduong.pawnmanagement.repository.AssetCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetCategoryService {
    private final AssetCategoryRepository assetCategoryRepository;
    private final AssetCategoryMapper assetCategoryMapper;

    public List<AssetCategoryResponse> getAll() {
        return assetCategoryMapper.toResponseList(assetCategoryRepository.findAll());
    }

    public AssetCategoryResponse getById(Long id) {
        return assetCategoryMapper.toResponse(assetCategoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Asset Category Not Found")));
    }

    public AssetCategoryResponse create(AssetCategoryRequest request) {
        if(assetCategoryRepository.existsByName(request.getName())){
            throw new DuplicateResourceException("Asset Category Name Already Exists");
        }
        return assetCategoryMapper.toResponse(assetCategoryRepository.save(assetCategoryMapper.toEntity(request)));
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
        return  assetCategoryMapper.toResponse(assetCategory);
    }

    public void deleteById(Long id) {
        if(!assetCategoryRepository.existsById(id)){
           throw new ResourceNotFoundException("Asset Category Not Found");
        }
        assetCategoryRepository.deleteById(id);
    }
}

