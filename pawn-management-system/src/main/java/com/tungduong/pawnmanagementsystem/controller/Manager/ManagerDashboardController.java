package com.tungduong.pawnmanagementsystem.controller.Manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ManagerDashboardController {
        @GetMapping()
        public String home() {
            return "manager/dashboard";
        }

}
