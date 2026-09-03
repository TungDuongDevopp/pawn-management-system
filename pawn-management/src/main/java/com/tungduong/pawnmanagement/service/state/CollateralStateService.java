package com.tungduong.pawnmanagement.service.state;

import com.tungduong.pawnmanagement.dto.request.CollateralAppraiseRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralResponse;
import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.CollateralMapper;
import com.tungduong.pawnmanagement.model.Collateral;
import com.tungduong.pawnmanagement.model.Staff;
import com.tungduong.pawnmanagement.model.enums.AssetStatus;
import com.tungduong.pawnmanagement.model.enums.Department;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import com.tungduong.pawnmanagement.repository.CollateralRepository;
import com.tungduong.pawnmanagement.repository.StaffRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CollateralStateService {
    private final StaffRepository staffRepository;
    private final CollateralRepository collateralRepository;
    private final CollateralMapper collateralMapper;

    private void ensureManipulable(
            Collateral collateral,
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

        if (staff != null ){
            if(staff.getRecordStatus() == RecordStatus.INACTIVE
                    || staff.getRecordStatus() == RecordStatus.DELETED) {
                throw new CanNotManipulateDataException("Staff cannot be manipulated in its current status");
            }


        }
    }

    @Transactional
    public CollateralResponse appraised(Long id, CollateralAppraiseRequest request) {
        Collateral currentCollateral = collateralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collateral not found with id " + id));
        Staff currentStaff = staffRepository.findById(request.getAppraisedBy())
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id " + request.getAppraisedBy()));
        ensureManipulable(currentCollateral, currentStaff);
        if (currentStaff.getDepartment() != Department.APPRAISER) {
            throw new CanNotManipulateDataException("Staff does not have permission to appraise collateral");
        }
        currentCollateral.setAppraisedAt(Instant.now());
        currentCollateral.setStatus(AssetStatus.APPROVED);
        currentCollateral.setAppraisedBy(currentStaff);
        currentCollateral.setAppraisedValue(request.getAppraisedValue());
        return collateralMapper.toResponse(currentCollateral);

    }
}
