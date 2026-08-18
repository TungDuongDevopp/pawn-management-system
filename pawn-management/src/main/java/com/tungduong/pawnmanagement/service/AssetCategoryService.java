package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.AssetCategoryRequest;
import com.tungduong.pawnmanagement.dto.request.update.AssetCategoryUpdateRequest;
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
                    "Asset Category has been deleted or inactivated and cannot be manipulated"
            );
        }
    }

    public Page<AssetCategoryResponse> getAll(Pageable pageable) {
        Specification<AssetCategory> spec = AssetCategorySpecification.statusNot(RecordStatus.DELETED);
        return assetCategoryRepository.findAll(spec, pageable).map(assetCategoryMapper::toResponse);
    }

    public AssetCategoryResponse getById(Long id) {
        return assetCategoryMapper.toResponse(assetCategoryRepository.findByIdAndRecordStatusNot(id,RecordStatus.DELETED).orElseThrow(()-> new ResourceNotFoundException("Asset Category not found")));
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
                .orElseThrow(() -> new ResourceNotFoundException("Asset Category Not Found"));

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

        if (request.getStatus() != null) {
            currentAssetCategory.setRecordStatus(request.getStatus());
        }

        return assetCategoryMapper.toResponse(currentAssetCategory);
    }

    @Transactional
    public void deleteById(Long id) {
        AssetCategory assetCategory = assetCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset Category Not Found"));

        ensureManipulable(assetCategory);

        assetCategory.setRecordStatus(RecordStatus.DELETED);
    }
}
