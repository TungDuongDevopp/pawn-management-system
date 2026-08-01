package com.tungduong.pawnmanagementsystem.controller;

import com.tungduong.pawnmanagementsystem.dto.request.RegisterRequest;
import com.tungduong.pawnmanagementsystem.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class AuthController {
    private final AccountService service ;

    public AuthController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("registerRequest",new RegisterRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterRequest registerRequest, BindingResult result){
        if(result.hasErrors()){
            return "auth/register";
        }
        if(service.isExistAccount(registerRequest.getUsername())){
            result.rejectValue("username", "username.exists", "Username đã tồn tại, vui lòng sử dụng email khác.");
            return "auth/register";
        }
        service.register(registerRequest);

        return "redirect:/login";
    }


}
