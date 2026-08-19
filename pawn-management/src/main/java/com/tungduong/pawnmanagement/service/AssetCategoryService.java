package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.AssetCategoryRequest;
import com.tungduong.pawnmanagement.dto.request.update.AssetCategoryUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.AssetCategoryResponse;
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
        if (assetCategory.getRecordStatus() == RecordStatus.DELETED
                || assetCategory.getRecordStatus() == RecordStatus.INACTIVE) {
            throw new CanNotManipulateDataException(
                    "Asset category cannot be manipulated in its current status"
            );
        }
    }

    public Page<AssetCategoryResponse> getAll(Pageable pageable) {
        Specification<AssetCategory> spec = AssetCategorySpecification.statusNot(RecordStatus.DELETED);
        return assetCategoryRepository.findAll(spec, pageable).map(assetCategoryMapper::toResponse);
    }

    public AssetCategoryResponse getById(Long id) {
        AssetCategory assetCategory = assetCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset category not found with id " + id));
        ensureManipulable(assetCategory);
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

        if (currentAssetCategory.getRecordStatus() == RecordStatus.DELETED) {
            throw new CanNotManipulateDataException("Asset category cannot be manipulated in its current status");
        }

        currentAssetCategory.setRecordStatus(request.getRecordStatus());
        return assetCategoryMapper.toResponse(currentAssetCategory);
    }

    @Transactional
    public void deleteById(Long id) {
        AssetCategory assetCategory = assetCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset category not found with id " + id));

        ensureManipulable(assetCategory);

        assetCategory.setRecordStatus(RecordStatus.DELETED);
    }
}
