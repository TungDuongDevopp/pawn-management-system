package com.tungduong.pawnmanagementsystem.service;

import com.tungduong.pawnmanagementsystem.helper.ResourceNotFoundException;
import com.tungduong.pawnmanagementsystem.model.Staff;
import com.tungduong.pawnmanagementsystem.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class StaffService {
    private final StaffRepository repository;

    public List<Staff> getAllStaffs() {
        return repository.findAll();
    }
    public Staff getStaffById(Long id){
        return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Staff not found"));
    }
    public Staff saveStaff(Staff staff){
        return repository.save(staff);
    }

    public boolean deleteStaffById(Long id){
        if(!repository.existsById(id)){
            return false;
        }

        repository.deleteById(id);
        return true;
    }
    public Staff updateStaff(Staff staff){

        Staff currentStaff = getStaffById(staff.getId());
        currentStaff.setFullname(staff.getFullname());
        currentStaff.setEmail(staff.getEmail());
        currentStaff.setPhone(staff.getPhone());
        currentStaff.setAddress(staff.getAddress());
        currentStaff.setDeparment(staff.getDeparment());
        currentStaff.setPosition(staff.getPosition());
        currentStaff.setSalary(staff.getSalary());
        currentStaff.setStatus(staff.getStatus());

        repository.save(currentStaff);
        return currentStaff;
    }
}
