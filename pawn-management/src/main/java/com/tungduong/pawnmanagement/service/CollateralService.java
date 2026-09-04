package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.filter.CollateralFilterRequest;
import com.tungduong.pawnmanagement.dto.request.CollateralRequest;
import com.tungduong.pawnmanagement.dto.request.update.CollateralUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralResponse;
import com.tungduong.pawnmanagement.helper.EntityGuard;
import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.CollateralMapper;
import com.tungduong.pawnmanagement.model.AssetType;
import com.tungduong.pawnmanagement.model.Collateral;
import com.tungduong.pawnmanagement.model.Customer;
import com.tungduong.pawnmanagement.model.enums.AssetStatus;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import com.tungduong.pawnmanagement.repository.AssetTypeRepository;
import com.tungduong.pawnmanagement.repository.CollateralRepository;
import com.tungduong.pawnmanagement.repository.CustomerRepository;
import com.tungduong.pawnmanagement.service.specification.CollateralSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollateralService {
    private final CollateralRepository collateralRepository;
    private final CollateralMapper collateralMapper;
    private final CustomerRepository customerRepository;
    private final AssetTypeRepository assetTypeRepository;
    private void ensureManipulable(
            Collateral collateral,
            Customer customer
    ) {
        if (collateral != null) {
            EntityGuard.requireManipulable(collateral, "Collateral");
            if (collateral.getStatus() == AssetStatus.REJECTED
                    || collateral.getStatus() == AssetStatus.RETURNED
                    || collateral.getStatus() == AssetStatus.LIQUIDATED
                    || collateral.getStatus() == AssetStatus.DAMAGED_LOST
                    || collateral.getStatus() == AssetStatus.CONFISCATED) {
                throw new CanNotManipulateDataException(
                        "Collateral cannot be manipulated in its current status"
                );
            }
        }

        if (customer != null) {
            EntityGuard.requireManipulable(customer, "Customer");
        }
    }
    public Page<CollateralResponse> findAll(Pageable pageable, CollateralFilterRequest request) {
        Specification<Collateral> specification = Specification.allOf(
                CollateralSpecification.recordStatusNot(RecordStatus.DELETED),
                CollateralSpecification.hasName(request),
                CollateralSpecification.hasDeclaredValue(request),
                CollateralSpecification.hasAppraisedValue(request),
                CollateralSpecification.hasCustomerId(request),
                CollateralSpecification.hasAssetTypeId(request),
                CollateralSpecification.hasStatus(request),
                CollateralSpecification.hasAppraisedByStaffId(request),
                CollateralSpecification.hasAppraisedAt(request)
        );
        return collateralRepository.findAll(specification, pageable).map(collateralMapper::toResponse);
    }

    public CollateralResponse findById(Long id) {
        Collateral collateral = collateralRepository.findByIdAndRecordStatusNot(id, RecordStatus.DELETED)
                .orElseThrow(() -> new ResourceNotFoundException("Collateral not found with id " + id));
        return collateralMapper.toResponse(collateral);
    }

    @Transactional
    public CollateralResponse create(CollateralRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + request.getCustomerId()));
        AssetType assetType = assetTypeRepository.findById(request.getAssetTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Asset type not found with id " + request.getAssetTypeId()));

        EntityGuard.requireAssignable(assetType, "Asset type");
        ensureManipulable(null, customer);
        Collateral collateral = collateralMapper.toEntity(request);
        collateral.setCustomer(customer);
        collateral.setType(assetType);
        collateral.setStatus(AssetStatus.UNDER_REVIEW);
        return collateralMapper.toResponse(collateralRepository.save(collateral));
    }

    @Transactional
    public CollateralResponse update(CollateralUpdateRequest request, Long id) {
        Collateral currentCollateral = collateralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collateral not found with id " + id));

        if(currentCollateral.getStatus() != AssetStatus.UNDER_REVIEW){
           throw new CanNotManipulateDataException("Collateral cannot be updated in its current status");
        }

        Customer customer = currentCollateral.getCustomer();

        AssetType assetType = null;

        if (request.getAssetTypeId() != null) {
            assetType = assetTypeRepository.findById(request.getAssetTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Asset type not found with id " + request.getAssetTypeId()));
            EntityGuard.requireAssignable(assetType, "Asset type");
        }

        ensureManipulable(currentCollateral, customer);

        if (request.getName() != null && !request.getName().isBlank()) {
            currentCollateral.setName(request.getName());
        }

        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            currentCollateral.setDescription(request.getDescription());
        }

        if (request.getDeclaredValue() != null) {
            currentCollateral.setDeclaredValue(request.getDeclaredValue());
        }


        if (assetType != null) {
            currentCollateral.setType(assetType);
        }

        return collateralMapper.toResponse(currentCollateral);
    }



    @Transactional
    public CollateralResponse updateRecordStatus(Long id, RecordStatusUpdateRequest request) {
        Collateral collateral = collateralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collateral not found with id " + id));

        EntityGuard.requireNotDeleted(collateral, "Collateral");

        collateral.setRecordStatus(request.getRecordStatus());
        return collateralMapper.toResponse(collateral);
    }

    @Transactional
    public void delete(Long id) {
        Collateral collateral = collateralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collateral not found with id " + id));
        ensureManipulable(collateral, null);
        collateral.setRecordStatus(RecordStatus.DELETED);
    }

}
