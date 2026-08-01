package com.tungduong.pawnmanagementsystem.controller.Staff;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
public class StaffDashboardController {
    @GetMapping()
    public String home() {
        return "staff/dashboard";
    }
}
