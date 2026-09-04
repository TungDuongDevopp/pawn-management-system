package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.CollateralDocumentTypeRequest;
import com.tungduong.pawnmanagement.dto.request.update.CollateralDocumentTypeUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralDocumentTypeResponse;
import com.tungduong.pawnmanagement.helper.EntityGuard;
import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.CollateralDocumentTypeMapper;
import com.tungduong.pawnmanagement.model.CollateralDocumentType;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import com.tungduong.pawnmanagement.repository.CollateralDocumentRepository;
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
    private final CollateralDocumentRepository collateralDocumentRepository;

    private void ensureManipulable(CollateralDocumentType collateralDocumentType) {
        if (collateralDocumentType != null) {
            EntityGuard.requireManipulable(collateralDocumentType, "Collateral document type");
        }
    }

    public Page<CollateralDocumentTypeResponse> findAll(Pageable pageable) {
        Specification<CollateralDocumentType> spec = CollateralDocumentTypeSpecification.recordStatusNot(RecordStatus.DELETED);
        return collateralDocumentTypeRepository.findAll(spec, pageable).map(collateralDocumentTypeMapper::toResponse);
    }

    public CollateralDocumentTypeResponse findById(Long id) {
        CollateralDocumentType collateralDocumentType = collateralDocumentTypeRepository.findByIdAndRecordStatusNot(id, RecordStatus.DELETED)
                .orElseThrow(() -> new ResourceNotFoundException("Collateral document type not found with id " + id));
        return collateralDocumentTypeMapper.toResponse(collateralDocumentType);
    }

    @Transactional
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

        return collateralDocumentTypeMapper.toResponse(currentCollateralDocumentType);
    }

    @Transactional
    public CollateralDocumentTypeResponse updateRecordStatus(Long id, RecordStatusUpdateRequest request) {
        CollateralDocumentType currentCollateralDocumentType = collateralDocumentTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collateral document type not found with id " + id));

        EntityGuard.requireNotDeleted(currentCollateralDocumentType, "Collateral document type");

        if (request.getRecordStatus() == RecordStatus.DELETED) {
            if (collateralDocumentRepository.existsByDocumentTypeId(id)) {
                throw new CanNotManipulateDataException("Collateral document type is in use and cannot be deleted");
            }
        }

        currentCollateralDocumentType.setRecordStatus(request.getRecordStatus());
        return collateralDocumentTypeMapper.toResponse(currentCollateralDocumentType);
    }

    @Transactional
    public void delete(Long id) {
        CollateralDocumentType collateralDocumentType = collateralDocumentTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collateral document type not found with id " + id));

        ensureManipulable(collateralDocumentType);

        if (collateralDocumentRepository.existsByDocumentTypeId(id)) {
            throw new CanNotManipulateDataException("Collateral document type is in use and cannot be deleted");
        }

        collateralDocumentType.setRecordStatus(RecordStatus.DELETED);
    }
}
