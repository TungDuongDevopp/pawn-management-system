package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.filter.StaffFilterRequest;
import com.tungduong.pawnmanagement.dto.request.StaffRequest;
import com.tungduong.pawnmanagement.dto.request.update.StaffUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.StaffResponse;
import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.StaffMapper;
import com.tungduong.pawnmanagement.model.Staff;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import com.tungduong.pawnmanagement.repository.StaffRepository;
import com.tungduong.pawnmanagement.service.specification.StaffSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;

    private void ensureManipulable(Staff staff) {
        if (staff.getRecordStatus() == RecordStatus.DELETED
                || staff.getRecordStatus() == RecordStatus.INACTIVE) {
            throw new CanNotManipulateDataException(
                    "Staff cannot be manipulated in its current status"
            );
        }
    }

    public Page<StaffResponse> getAll(Pageable pageable, StaffFilterRequest request) {
        Specification<Staff> spec = Specification.allOf(
                StaffSpecification.recordStatusNot(RecordStatus.DELETED),
                StaffSpecification.hasFullName(request),
                StaffSpecification.hasAddress(request),
                StaffSpecification.hasEmail(request),
                StaffSpecification.hasPhone(request),
                StaffSpecification.hasSalary(request),
                StaffSpecification.hasDepartment(request),
                StaffSpecification.hasPosition(request)
        );
        return staffRepository.findAll(spec, pageable).map(staffMapper::toResponse);
    }

    public StaffResponse getById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id " + id));
        ensureManipulable(staff);
        return staffMapper.toResponse(staff);
    }

    public StaffResponse create(StaffRequest staffRequest) {
        if (staffRepository.existsByPhoneAndRecordStatusNot(staffRequest.getPhone(), RecordStatus.DELETED)) {
            throw new DuplicateResourceException("Phone number already exists");
        }
        if (staffRequest.getEmail() != null && !staffRequest.getEmail().isBlank()
                && staffRepository.existsByEmailAndRecordStatusNot(staffRequest.getEmail(), RecordStatus.DELETED)) {
            throw new DuplicateResourceException("Email already exists");
        }
        return staffMapper.toResponse(staffRepository.save(staffMapper.toEntity(staffRequest)));
    }

    @Transactional
    public StaffResponse update(StaffUpdateRequest staffRequest, Long id) {
        Staff currentStaff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id " + id));

        ensureManipulable(currentStaff);

        if (staffRequest.getEmail() != null && !staffRequest.getEmail().isBlank()) {
            if (staffRepository.existsByEmailAndIdNotAndRecordStatusNot(staffRequest.getEmail(), id, RecordStatus.DELETED)) {
                throw new DuplicateResourceException("Email already exists");
            }
            currentStaff.setEmail(staffRequest.getEmail());
        }

        if (staffRequest.getPhone() != null && !staffRequest.getPhone().isBlank()) {
            if (staffRepository.existsByPhoneAndIdNotAndRecordStatusNot(staffRequest.getPhone(), id, RecordStatus.DELETED)) {
                throw new DuplicateResourceException("Phone already exists");
            }
            currentStaff.setPhone(staffRequest.getPhone());
        }

        if (staffRequest.getAddress() != null && !staffRequest.getAddress().isBlank()) {
            currentStaff.setAddress(staffRequest.getAddress());
        }

        if (staffRequest.getFullname() != null && !staffRequest.getFullname().isBlank()) {
            currentStaff.setFullname(staffRequest.getFullname());
        }

        if (staffRequest.getSalary() != null) {
            currentStaff.setSalary(staffRequest.getSalary());
        }

        if (staffRequest.getDepartment() != null) {
            currentStaff.setDepartment(staffRequest.getDepartment());
        }

        if (staffRequest.getPosition() != null) {
            currentStaff.setPosition(staffRequest.getPosition());
        }

        return staffMapper.toResponse(currentStaff);
    }

    @Transactional
    public StaffResponse updateRecordStatus(Long id, RecordStatusUpdateRequest request) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id " + id));

        if (staff.getRecordStatus() == RecordStatus.DELETED) {
            throw new CanNotManipulateDataException("Staff cannot be manipulated in its current status");
        }

        staff.setRecordStatus(request.getRecordStatus());
        return staffMapper.toResponse(staff);
    }

    @Transactional
    public void delete(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id " + id));

        ensureManipulable(staff);

        staff.setRecordStatus(RecordStatus.DELETED);
    }
}


