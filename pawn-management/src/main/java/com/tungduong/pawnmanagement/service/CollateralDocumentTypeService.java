package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.CollateralDocumentTypeRequest;
import com.tungduong.pawnmanagement.dto.request.update.CollateralDocumentTypeUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralDocumentTypeResponse;
import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.CollateralDocumentTypeMapper;
import com.tungduong.pawnmanagement.model.CollateralDocumentType;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import com.tungduong.pawnmanagement.repository.CollateralDocumentTypeRepository;
import com.tungduong.pawnmanagement.service.specification.CollateralDocumentTypeSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollateralDocumentTypeService {
    private final CollateralDocumentTypeRepository collateralDocumentTypeRepository;
    private final CollateralDocumentTypeMapper collateralDocumentTypeMapper;

    private void ensureManipulable(CollateralDocumentType collateralDocumentType) {
        if (collateralDocumentType.getRecordStatus() == RecordStatus.DELETED
                || collateralDocumentType.getRecordStatus() == RecordStatus.INACTIVE) {
            throw new CanNotManipulateDataException(
                    "Collateral document type cannot be manipulated in its current status"
            );
        }
    }

    public Page<CollateralDocumentTypeResponse> getAll(Pageable pageable) {
        Specification<CollateralDocumentType> spec = CollateralDocumentTypeSpecification.statusNot(RecordStatus.DELETED);
        return collateralDocumentTypeRepository.findAll(spec, pageable).map(collateralDocumentTypeMapper::toResponse);
    }

    public CollateralDocumentTypeResponse getById(Long id) {
        CollateralDocumentType collateralDocumentType = collateralDocumentTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collateral document type not found with id " + id));
        ensureManipulable(collateralDocumentType);
        return collateralDocumentTypeMapper.toResponse(collateralDocumentType);
    }

    public CollateralDocumentTypeResponse create(CollateralDocumentTypeRequest request) {
        if (collateralDocumentTypeRepository.existsByNameAndRecordStatusNot(request.getName(), RecordStatus.DELETED)) {
            throw new DuplicateResourceException("Collateral Document Type Name Already Exists");
        }
        CollateralDocumentType collateralDocumentType = collateralDocumentTypeMapper.toEntity(request);
        collateralDocumentType.setRecordStatus(RecordStatus.ACTIVE);
        return collateralDocumentTypeMapper.toResponse(collateralDocumentTypeRepository.save(collateralDocumentType));
    }

    @Transactional
    public CollateralDocumentTypeResponse update(CollateralDocumentTypeUpdateRequest request, Long id) {
        CollateralDocumentType currentCollateralDocumentType = collateralDocumentTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collateral document type not found with id " + id));

        ensureManipulable(currentCollateralDocumentType);

        if (request.getName() != null && !request.getName().isBlank()) {
            if (collateralDocumentTypeRepository.existsByNameAndIdNotAndRecordStatusNot(request.getName(), id, RecordStatus.DELETED)) {
                throw new DuplicateResourceException("Collateral Document Type Name Already Exists");
            }
            currentCollateralDocumentType.setName(request.getName());
        }

        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            currentCollateralDocumentType.setDescription(request.getDescription());
        }

        if (request.getStatus() != null) {
            currentCollateralDocumentType.setRecordStatus(request.getStatus());
        }

        return collateralDocumentTypeMapper.toResponse(currentCollateralDocumentType);
    }

    @Transactional
    public void deleteById(Long id) {
        CollateralDocumentType collateralDocumentType = collateralDocumentTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collateral document type not found with id " + id));

        ensureManipulable(collateralDocumentType);

        collateralDocumentType.setRecordStatus(RecordStatus.DELETED);
    }
}
