package com.tungduong.pawnmanagementsystem.controller;

import com.tungduong.pawnmanagementsystem.model.Account;
import com.tungduong.pawnmanagementsystem.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "Admin/Account/index";
    }

    @GetMapping("/create")
    public String createAccount(Model model){
        model.addAttribute("account",new Account());
        return "Admin/Account/create";
    }

    @PostMapping("/create")
    public String createAccount(@ModelAttribute Account account){
        service.createAccount(account);
        return "redirect:/accounts";
    }

    @GetMapping("/{id}")
    public String updateAccount(Model model, @PathVariable Long id){
        Account currentAccount = service.getAccountById(id);
        model.addAttribute("account",currentAccount);
        return "Admin/Account/update";
    }
    @PostMapping("/update")
    public String updateAccount(@ModelAttribute Account updateAccount){
        service.updateAccount(updateAccount);
        return "redirect:/accounts";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id){
        service.deleteAccount(id);
        return "redirect:/accounts";
    }


}