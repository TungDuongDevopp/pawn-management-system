package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.filter.StaffFilterRequest;
import com.tungduong.pawnmanagement.dto.request.StaffRequest;
import com.tungduong.pawnmanagement.dto.request.update.StaffUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.StaffResponse;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.StaffMapper;
import com.tungduong.pawnmanagement.model.Staff;
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

  public Page<StaffResponse> getAll(Pageable pageable, StaffFilterRequest request) {
      Specification<Staff> spec = Specification.allOf(
              StaffSpecification.hasFullName(request),
              StaffSpecification.hasAddress(request),
              StaffSpecification.hasEmail(request),
              StaffSpecification.hasPhone(request),
              StaffSpecification.hasSalary(request),
              StaffSpecification.hasDepartment(request),
              StaffSpecification.hasPosition(request)
      );
    return staffRepository.findAll(spec,pageable).map(staffMapper::toDto);
  }

  public StaffResponse getById(Long id) {
      return staffMapper.toDto(staffRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Staff Not Found")));
  }

  public StaffResponse create(StaffRequest staffRequest) {
      if(staffRepository.existsByPhone(staffRequest.getPhone())) {
          throw new DuplicateResourceException("Phone number already exists");
      }
      if(staffRepository.existsByEmail(staffRequest.getEmail())) {
          throw new DuplicateResourceException("Email already exists");
      }
      return staffMapper.toDto(staffRepository.save(staffMapper.toEntity(staffRequest)));
  }
  @Transactional
  public StaffResponse update(StaffUpdateRequest staffRequest,Long id) {

      Staff currentStaff = staffRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Staff Not Found"));

        if(staffRequest.getEmail() != null && !staffRequest.getEmail().isBlank()) {
            if(staffRepository.existsByEmail(staffRequest.getEmail()) && !id.equals(staffRequest.getId())) {
              throw new DuplicateResourceException("Email already exists");
            }
            currentStaff.setEmail(staffRequest.getEmail());

        }
      if(staffRequest.getPhone() != null && !staffRequest.getPhone().isBlank()) {
          if(staffRepository.existsByPhone(staffRequest.getPhone()) && !id.equals(staffRequest.getId())) {
              throw new DuplicateResourceException("Phone already exists");
          }
          currentStaff.setPhone(staffRequest.getPhone());

      }
      if(staffRequest.getAddress() != null && !staffRequest.getAddress().isBlank()) {
          currentStaff.setAddress(staffRequest.getAddress());
      }
      if(staffRequest.getFullname() != null && !staffRequest.getFullname().isBlank()) {
          currentStaff.setFullname(staffRequest.getFullname());
      }
      if(staffRequest.getSalary() != null){
          currentStaff.setSalary(staffRequest.getSalary());
      }
      if(staffRequest.getDepartment() != null){
          currentStaff.setDepartment(staffRequest.getDepartment());
      }
      if(staffRequest.getPosition() != null){
          currentStaff.setPosition(staffRequest.getPosition());
      }
      return staffMapper.toDto(currentStaff);
  }

  public void delete(Long id) {
      if(!staffRepository.existsById(id)) {
          throw new ResourceNotFoundException("Staff Not Found");
      }
      staffRepository.deleteById(id);
  }
}
