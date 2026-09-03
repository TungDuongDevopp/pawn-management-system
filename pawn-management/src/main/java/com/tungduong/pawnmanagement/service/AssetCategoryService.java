package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.AssetCategoryRequest;
import com.tungduong.pawnmanagement.dto.request.update.AssetCategoryUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.AssetCategoryResponse;
import com.tungduong.pawnmanagement.helper.EntityGuard;
import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.AssetCategoryMapper;
import com.tungduong.pawnmanagement.model.AssetCategory;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import com.tungduong.pawnmanagement.repository.AssetCategoryRepository;
import com.tungduong.pawnmanagement.service.specification.AssetCategorySpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetCategoryService {
    private final AssetCategoryRepository assetCategoryRepository;
    private final AssetCategoryMapper assetCategoryMapper;


    private void ensureManipulable(AssetCategory assetCategory) {
        if (assetCategory != null) {
            EntityGuard.requireManipulable(assetCategory, "Asset category");
        }
    }

    public Page<AssetCategoryResponse> findAll(Pageable pageable) {
        Specification<AssetCategory> spec = AssetCategorySpecification.recordStatusNot(RecordStatus.DELETED);
        return assetCategoryRepository.findAll(spec, pageable).map(assetCategoryMapper::toResponse);
    }

    public AssetCategoryResponse findById(Long id) {
        AssetCategory assetCategory = assetCategoryRepository.findByIdAndRecordStatusNot(id, RecordStatus.DELETED)
                .orElseThrow(() -> new ResourceNotFoundException("Asset category not found with id " + id));
        return assetCategoryMapper.toResponse(assetCategory);
    }

    public AssetCategoryResponse create(AssetCategoryRequest request) {
        if (assetCategoryRepository.existsByNameAndRecordStatusNot(request.getName(), RecordStatus.DELETED)) {
            throw new DuplicateResourceException("Asset Category Name Already Exists");
        }
        AssetCategory assetCategory = assetCategoryMapper.toEntity(request);
        assetCategory.setRecordStatus(RecordStatus.ACTIVE);
        return assetCategoryMapper.toResponse(assetCategoryRepository.save(assetCategory));
    }

    @Transactional
    public AssetCategoryResponse update(AssetCategoryUpdateRequest request, Long id) {
        AssetCategory currentAssetCategory = assetCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset category not found with id " + id));

        ensureManipulable(currentAssetCategory);

        if (request.getName() != null && !request.getName().isBlank()) {
            if (assetCategoryRepository.existsByNameAndIdNotAndRecordStatusNot(request.getName(), id, RecordStatus.DELETED)) {
                throw new DuplicateResourceException("Asset Category Name Already Exists");
            }
            currentAssetCategory.setName(request.getName());
        }

        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            currentAssetCategory.setDescription(request.getDescription());
        }

        return assetCategoryMapper.toResponse(currentAssetCategory);
    }

    @Transactional
    public AssetCategoryResponse updateRecordStatus(Long id, RecordStatusUpdateRequest request) {
        AssetCategory currentAssetCategory = assetCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset category not found with id " + id));

        EntityGuard.requireNotDeleted(currentAssetCategory, "Asset category");

        currentAssetCategory.setRecordStatus(request.getRecordStatus());
        return assetCategoryMapper.toResponse(currentAssetCategory);
    }

    @Transactional
    public void delete(Long id) {
        AssetCategory assetCategory = assetCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset category not found with id " + id));

        ensureManipulable(assetCategory);

        assetCategory.setRecordStatus(RecordStatus.DELETED);
    }
}
