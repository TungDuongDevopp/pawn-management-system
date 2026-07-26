package com.tungduong.pawnmanagementsystem.controller;

import com.tungduong.pawnmanagementsystem.model.Account;
import com.tungduong.pawnmanagementsystem.model.Customer;
import com.tungduong.pawnmanagementsystem.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService service;

    public CustomerController (CustomerService service) {

        this.service = service;
    }

    @GetMapping
    public String getCustomer(Model model){
        List<Customer> customers = service.getAllCustomer();

        model.addAttribute("customers", customers);
        return "Admin/Customer/index";
    }

    @GetMapping("/create")
    public String createCustomer(Model model){
        model.addAttribute("customer",new Customer());
        return "Admin/Customer/create";
    }

    @PostMapping("/create")
    public String createCustomer(@ModelAttribute Customer customer){
        service.saveCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/{id}")
    public String updateCustomer(Model model, @PathVariable Long id){
        Customer currentCustomer = service.getCustomerById(id).orElse(null);
        model.addAttribute("customer",currentCustomer);
        return "Admin/Customer/update";
    }
    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute Customer updateCustomer){
        service.updateCustomer(updateCustomer);
        return "redirect:/customers";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id){
        service.deleteCustomer(id);
        return "redirect:/customers";
    }

}
