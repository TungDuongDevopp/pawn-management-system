package com.tungduong.pawnmanagementsystem.controller;

import com.tungduong.pawnmanagementsystem.model.Account;
import com.tungduong.pawnmanagementsystem.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String createAccount(@Valid @ModelAttribute Account account, BindingResult result){
        if(result.hasErrors()){
            return "Admin/Account/create";
        }
        service.saveAccount(account);
        return "redirect:/accounts";
    }

    @GetMapping("/{id}")
    public String updateAccount(Model model, @PathVariable Long id){
        Account currentAccount = service.getAccountById(id).orElse(null);
        model.addAttribute("account",currentAccount);
        return "Admin/Account/update";
    }
    @PostMapping("/update")
    public String updateAccount( @Valid @ModelAttribute Account updateAccount,BindingResult result){
        if(result.hasErrors()){
            return "Admin/Account/update";
        }
        service.updateAccount(updateAccount);
        return "redirect:/accounts";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id){
        service.deleteAccount(id);
        return "redirect:/accounts";
    }


}