package com.tungduong.pawnmanagementsystem.controller;

import com.tungduong.pawnmanagementsystem.model.Staff;
import com.tungduong.pawnmanagementsystem.model.enums.Department;
import com.tungduong.pawnmanagementsystem.model.enums.StaffStatus;
import com.tungduong.pawnmanagementsystem.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/staffs")
public class StaffController {
    private final StaffService service;

    public StaffController(StaffService service) {
        this.service = service;
    }

    @GetMapping
    public String getStaff(Model model){
        List<Staff> staff = service.getAllStaffs();

        model.addAttribute("staffs", staff);
        return "Admin/Staff/index";
    }

    @GetMapping("/create")
    public String createStaff(Model model){
        model.addAttribute("staff",new Staff());
        model.addAttribute("departments", Department.values());
        model.addAttribute("statuses", StaffStatus.values());
        return "Admin/Staff/create";
    }

    @PostMapping("/create")
    public String createStaff(@Valid @ModelAttribute Staff staff, BindingResult result){
        if(result.hasErrors()){
            return "Admin/Staff/create";
        }
        service.saveStaff(staff);
        return "redirect:/staffs";
    }

    @GetMapping("/{id}")
    public String updateStaff(Model model, @PathVariable Long id){
        Staff currentStaff = service.getStaffById(id).orElse(null);
        model.addAttribute("staff",currentStaff);
        model.addAttribute("departments", Department.values());
        model.addAttribute("statuses", StaffStatus.values());
        return "Admin/Staff/update";
    }
    @PostMapping("/update")
    public String updateStaff(@Valid @ModelAttribute Staff updateStaff, BindingResult result){
        if(result.hasErrors()){
            return "Admin/Staff/update";

        }
        service.updateStaff(updateStaff);
        return "redirect:/staffs";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStaff(@PathVariable Long id){
        service.deleteStaffById(id);
        return "redirect:/staffs";
    }



}
