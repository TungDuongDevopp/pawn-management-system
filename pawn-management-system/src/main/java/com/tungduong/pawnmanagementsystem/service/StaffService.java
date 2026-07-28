package com.tungduong.pawnmanagementsystem.service;

import com.tungduong.pawnmanagementsystem.model.Staff;
import com.tungduong.pawnmanagementsystem.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class StaffService {
    private final StaffRepository repository;

    public StaffService(StaffRepository repository) {
        this.repository = repository;
    }

    public List<Staff> getAllStaffs() {
        return repository.findAll();
    }
    public Optional<Staff> getStaffById(Long id){
        return repository.findById(id);
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
        Optional<Staff> optional = getStaffById(staff.getId());

        if(optional.isEmpty()){
            return null;
        }

        Staff currentStaff = optional.get();
        currentStaff.setFullname(staff.getFullname());
        currentStaff.setEmail(staff.getEmail());
        currentStaff.setPhone(staff.getPhone());
        currentStaff.setAddress(staff.getAddress());
        currentStaff.setDeparment(staff.getDeparment());
        currentStaff.setPosition(staff.getPosition());
        currentStaff.setSalary(staff.getSalary());
        currentStaff.setSatus(staff.getSatus());

        repository.save(currentStaff);
        return currentStaff;
    }
}
