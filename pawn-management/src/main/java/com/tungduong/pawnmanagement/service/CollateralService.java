package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.CollateralAppraiseRequest;
import com.tungduong.pawnmanagement.dto.request.CollateralRequest;
import com.tungduong.pawnmanagement.dto.request.update.CollateralUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralResponse;
import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.CollateralMapper;
import com.tungduong.pawnmanagement.model.AssetType;
import com.tungduong.pawnmanagement.model.Collateral;
import com.tungduong.pawnmanagement.model.Customer;
import com.tungduong.pawnmanagement.model.Staff;
import com.tungduong.pawnmanagement.model.enums.AssetStatus;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import com.tungduong.pawnmanagement.repository.AssetTypeRepository;
import com.tungduong.pawnmanagement.repository.CollateralRepository;
import com.tungduong.pawnmanagement.repository.CustomerRepository;
import com.tungduong.pawnmanagement.repository.StaffRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollateralService {
    private final CollateralRepository collateralRepository;
    private final CollateralMapper collateralMapper;
    private final CustomerRepository customerRepository;
    private final AssetTypeRepository assetTypeRepository;
    private final StaffRepository staffRepository;

    private void ensureManipulable(
            Collateral collateral,
            AssetType assetType,
            Customer customer,
            Staff staff
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
                    "AssetType cannot be manipulated in its current status"
            );
        }

        if (customer != null &&
                (customer.getRecordStatus() == RecordStatus.INACTIVE
                        || customer.getRecordStatus() == RecordStatus.DELETED)) {
            throw new CanNotManipulateDataException(
                    "Customer cannot be manipulated in its current status"
            );
        }

        if (staff != null &&
                (staff.getRecordStatus() == RecordStatus.INACTIVE
                        || staff.getRecordStatus() == RecordStatus.DELETED)) {
            throw new CanNotManipulateDataException(
                    "Staff cannot be manipulated in its current status"
            );
        }
    }
    public List<CollateralResponse> getAll() {
        return collateralMapper.toResponseList(collateralRepository.findAll());
    }

    public CollateralResponse getById(Long id) {
        return collateralMapper.toDto(collateralRepository.findByIdAndRecordStatusNot(id, RecordStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException("Collateral not found")));
    }

    public CollateralResponse create(CollateralRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        AssetType assetType = assetTypeRepository.findById(request.getAssetTypeId()).orElseThrow(() -> new ResourceNotFoundException("AssetType not found"));

        ensureManipulable(null, assetType, customer,null);
        Collateral collateral = collateralMapper.toEntity(request);
        collateral.setCustomer(customer);
        collateral.setType(assetType);
        return collateralMapper.toDto(collateralRepository.save(collateral));
    }

    @Transactional
    public CollateralResponse update(CollateralUpdateRequest request, Long id) {
        Collateral currentCollateral = collateralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collateral not found"));

        Customer customer = null;
        AssetType assetType = null;

        if (request.getCustomerId() != null) {
            customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        }

        if (request.getAssetTypeId() != null) {
            assetType = assetTypeRepository.findById(request.getAssetTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("AssetType not found"));
        }

        ensureManipulable(currentCollateral, assetType, customer, null);

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

        return collateralMapper.toDto(currentCollateral);
    }

    @Transactional
    public CollateralResponse appraised(Long id, CollateralAppraiseRequest request) {
        Collateral currentCollateral = collateralRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Collateral not found"));
        Staff currentStaff = staffRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Staff not found"));
        ensureManipulable(currentCollateral,null,null,currentStaff);
        currentCollateral.setAppraisedAt(Instant.now());
        currentCollateral.setStatus(AssetStatus.APPROVED);
        currentCollateral.setAppraisedBy(currentStaff);
        currentCollateral.setAppraisedValue(request.getAppraisedValue());
        return collateralMapper.toDto(currentCollateral);

    }

    @Transactional
    public void delete(Long id) {
        Collateral collateral = collateralRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Collateral not found"));
        ensureManipulable(collateral,null,null,null);
        collateral.setRecordStatus(RecordStatus.DELETED);
    }

}
