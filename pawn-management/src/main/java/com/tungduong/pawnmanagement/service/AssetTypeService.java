package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.AssetTypeRequest;
import com.tungduong.pawnmanagement.dto.request.update.AssetTypeUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.AssetTypeResponse;
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
        if (assetType != null &&
                (assetType.getRecordStatus() == RecordStatus.DELETED
                || assetType.getRecordStatus() == RecordStatus.INACTIVE)) {
            throw new CanNotManipulateDataException(
                    "Asset Type has been deleted or inactivated and cannot be manipulated"
            );
        }

        if (assetCategory != null && (assetCategory.getRecordStatus() == RecordStatus.DELETED
                || assetCategory.getRecordStatus() == RecordStatus.INACTIVE)) {
            throw new CanNotManipulateDataException(
                    "Asset Category has been deleted or inactivated and cannot be manipulated"
            );
        }
    }

    public Page<AssetTypeResponse> getAll(Pageable pageable) {
        Specification<AssetType> spec = AssetTypeSpecification.statusNot(RecordStatus.DELETED);
        return assetTypeRepository.findAll(spec, pageable).map(assetTypeMapper::toResponse);
    }

    public AssetTypeResponse getById(Long id) {
        return assetTypeMapper.toResponse(assetTypeRepository.findByIdAndRecordStatusNot(id,RecordStatus.DELETED).orElseThrow(()-> new ResourceNotFoundException("Asset Type not found")));
    }

    public AssetTypeResponse create(AssetTypeRequest request) {
        if (assetTypeRepository.existsByNameAndRecordStatusNot(request.getName(), RecordStatus.DELETED)) {
            throw new DuplicateResourceException("Asset Type Name Already Exists");
        }
        AssetCategory assetCategory = assetCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Asset Category Not Found"));

        ensureManipulable(null, assetCategory);

        AssetType assetType = assetTypeMapper.toEntity(request);
        assetType.setCategory(assetCategory);
        assetType.setRecordStatus(RecordStatus.ACTIVE);
        return assetTypeMapper.toResponse(assetTypeRepository.save(assetType));
    }

    @Transactional
    public AssetTypeResponse update(AssetTypeUpdateRequest request, Long id) {
        AssetType currentAssetType = assetTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset Type Not Found"));

        AssetCategory assetCategory = null;
        if (request.getCategoryId() != null) {
            assetCategory = assetCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Asset Category Not Found"));
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
    public void deleteById(Long id) {
        AssetType assetType = assetTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset Type Not Found"));

        ensureManipulable(assetType, null);
        assetType.setRecordStatus(RecordStatus.DELETED);
    }
}
