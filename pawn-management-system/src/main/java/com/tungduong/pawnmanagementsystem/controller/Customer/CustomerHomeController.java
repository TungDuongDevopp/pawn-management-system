package com.tungduong.pawnmanagementsystem.controller.Customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerHomeController {
    @GetMapping()
    public String home() {
        return "customer/home";
    }
}
