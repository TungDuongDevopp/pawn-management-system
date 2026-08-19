package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.filter.CollateralFilterRequest;
import com.tungduong.pawnmanagement.dto.request.CollateralRequest;
import com.tungduong.pawnmanagement.dto.request.update.CollateralUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralResponse;
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
            AssetType assetType,
            Customer customer
    ) {
        if (collateral != null &&
                (collateral.getRecordStatus() == RecordStatus.INACTIVE
                        || collateral.getRecordStatus() == RecordStatus.DELETED
                        || collateral.getStatus() == AssetStatus.REJECTED
                        || collateral.getStatus() == AssetStatus.RETURNED
                        || collateral.getStatus() == AssetStatus.LIQUIDATED
                        || collateral.getStatus() == AssetStatus.DAMAGED_LOST
                        || collateral.getStatus() == AssetStatus.CONFISCATED)) {

            throw new CanNotManipulateDataException(
                    "Collateral cannot be manipulated in its current status"
            );
        }

        if (assetType != null &&
                (assetType.getRecordStatus() == RecordStatus.INACTIVE
                        || assetType.getRecordStatus() == RecordStatus.DELETED)) {
            throw new CanNotManipulateDataException(
                    "Asset type cannot be manipulated in its current status"
            );
        }

        if (customer != null &&
                (customer.getRecordStatus() == RecordStatus.INACTIVE
                        || customer.getRecordStatus() == RecordStatus.DELETED)) {
            throw new CanNotManipulateDataException(
                    "Customer cannot be manipulated in its current status"
            );
        }

    }
    public Page<CollateralResponse> getAll(Pageable pageable, CollateralFilterRequest request) {
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

    public CollateralResponse getById(Long id) {
        Collateral collateral = collateralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collateral not found with id " + id));
        ensureManipulable(collateral, null, null);
        return collateralMapper.toResponse(collateral);
    }

    public CollateralResponse create(CollateralRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + request.getCustomerId()));
        AssetType assetType = assetTypeRepository.findById(request.getAssetTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Asset type not found with id " + request.getAssetTypeId()));

        ensureManipulable(null, assetType, customer);
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

        Customer customer = null;
        AssetType assetType = null;

        if (request.getCustomerId() != null) {
            customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + request.getCustomerId()));
        }

        if (request.getAssetTypeId() != null) {
            assetType = assetTypeRepository.findById(request.getAssetTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Asset type not found with id " + request.getAssetTypeId()));
        }

        ensureManipulable(currentCollateral, assetType, customer);

        if (request.getName() != null && !request.getName().isBlank()) {
            currentCollateral.setName(request.getName());
        }

        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            currentCollateral.setDescription(request.getDescription());
        }

        if (request.getDeclaredValue() != null) {
            currentCollateral.setDeclaredValue(request.getDeclaredValue());
        }

        if (customer != null) {
            currentCollateral.setCustomer(customer);
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

        if (collateral.getRecordStatus() == RecordStatus.DELETED) {
            throw new CanNotManipulateDataException("Collateral cannot be manipulated in its current status");
        }

        collateral.setRecordStatus(request.getRecordStatus());
        return collateralMapper.toResponse(collateral);
    }

    @Transactional
    public void delete(Long id) {
        Collateral collateral = collateralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collateral not found with id " + id));
        ensureManipulable(collateral, null, null);
        collateral.setRecordStatus(RecordStatus.DELETED);
    }

}
