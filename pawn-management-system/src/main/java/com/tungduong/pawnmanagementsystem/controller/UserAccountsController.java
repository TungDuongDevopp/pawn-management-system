package com.tungduong.pawnmanagementsystem.controller;

import com.tungduong.pawnmanagementsystem.model.Account;
import com.tungduong.pawnmanagementsystem.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/accounts")
public class UserAccountsController {

    private final AccountService service;

    public UserAccountsController (AccountService service) {

        this.service = service;
    }

    @GetMapping
    public String getAccount(Model model){
        List<Account> accounts = service.getAllAccounts();

        model.addAttribute("accounts", accounts);
        return "/html/AccountManagement";
    }
}