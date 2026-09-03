package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.AssetTypeRequest;
import com.tungduong.pawnmanagement.dto.request.update.AssetTypeUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.AssetTypeResponse;
import com.tungduong.pawnmanagement.helper.EntityGuard;
import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.AssetTypeMapper;
import com.tungduong.pawnmanagement.model.AssetCategory;
import com.tungduong.pawnmanagement.model.AssetType;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import com.tungduong.pawnmanagement.repository.AssetCategoryRepository;
import com.tungduong.pawnmanagement.repository.AssetTypeRepository;
import com.tungduong.pawnmanagement.service.specification.AssetTypeSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetTypeService {
    private final AssetTypeRepository assetTypeRepository;
    private final AssetCategoryRepository assetCategoryRepository;
    private final AssetTypeMapper assetTypeMapper;

    private void ensureManipulable(AssetType assetType, AssetCategory assetCategory) {
        if (assetType != null) {
            EntityGuard.requireManipulable(assetType, "Asset type");
        }
        if (assetCategory != null) {
            EntityGuard.requireManipulable(assetCategory, "Asset category");
        }
    }

    public Page<AssetTypeResponse> findAll(Pageable pageable) {
        Specification<AssetType> spec = AssetTypeSpecification.recordStatusNot(RecordStatus.DELETED);
        return assetTypeRepository.findAll(spec, pageable).map(assetTypeMapper::toResponse);
    }

    public AssetTypeResponse findById(Long id) {
        AssetType assetType = assetTypeRepository.findByIdAndRecordStatusNot(id, RecordStatus.DELETED)
                .orElseThrow(() -> new ResourceNotFoundException("Asset type not found with id " + id));
        return assetTypeMapper.toResponse(assetType);
    }

    public AssetTypeResponse create(AssetTypeRequest request) {
        if (assetTypeRepository.existsByNameAndRecordStatusNot(request.getName(), RecordStatus.DELETED)) {
            throw new DuplicateResourceException("Asset Type Name Already Exists");
        }
        AssetCategory assetCategory = assetCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Asset category not found with id " + request.getCategoryId()));

        ensureManipulable(null, assetCategory);

        AssetType assetType = assetTypeMapper.toEntity(request);
        assetType.setCategory(assetCategory);
        assetType.setRecordStatus(RecordStatus.ACTIVE);
        return assetTypeMapper.toResponse(assetTypeRepository.save(assetType));
    }

    @Transactional
    public AssetTypeResponse update(AssetTypeUpdateRequest request, Long id) {
        AssetType currentAssetType = assetTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset type not found with id " + id));

        AssetCategory assetCategory = null;
        if (request.getCategoryId() != null) {
            assetCategory = assetCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Asset category not found with id " + request.getCategoryId()));
        }

        ensureManipulable(currentAssetType, assetCategory);

        if (request.getName() != null && !request.getName().isBlank()) {
            if (assetTypeRepository.existsByNameAndIdNotAndRecordStatusNot(request.getName(), id, RecordStatus.DELETED)) {
                throw new DuplicateResourceException("Asset Type Name Already Exists");
            }
            currentAssetType.setName(request.getName());
        }

        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            currentAssetType.setDescription(request.getDescription());
        }

        if (assetCategory != null) {
            currentAssetType.setCategory(assetCategory);
        }

        return assetTypeMapper.toResponse(currentAssetType);
    }

    @Transactional
    public AssetTypeResponse updateRecordStatus(Long id, RecordStatusUpdateRequest request) {
        AssetType currentAssetType = assetTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset type not found with id " + id));

        EntityGuard.requireNotDeleted(currentAssetType, "Asset type");

        currentAssetType.setRecordStatus(request.getRecordStatus());
        return assetTypeMapper.toResponse(currentAssetType);
    }

    @Transactional
    public void delete(Long id) {
        AssetType assetType = assetTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset type not found with id " + id));

        ensureManipulable(assetType, null);
        assetType.setRecordStatus(RecordStatus.DELETED);
    }
}
