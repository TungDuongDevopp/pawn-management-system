package com.tungduong.pawnmanagementsystem.controller.Admin;

import com.tungduong.pawnmanagementsystem.model.Customer;
import com.tungduong.pawnmanagementsystem.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@Controller
@RequestMapping("/customers")
public class AdminCustomerController {
    private final CustomerService service;

    @GetMapping
    public String getCustomer(Model model){
        List<Customer> customers = service.getAllCustomer();
        model.addAttribute("customers", customers);
        return "admin/Customer/index";
    }

    @GetMapping("/create")
    public String createCustomer(Model model){
        model.addAttribute("customer",new Customer());
        return "admin/Customer/create";
    }

    @PostMapping("/create")
    public String createCustomer(@Valid @ModelAttribute Customer customer, BindingResult result){
        if(result.hasErrors()){
            return "admin/Customer/create";
        }
        service.saveCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/{id}")
    public String updateCustomer(Model model, @PathVariable Long id){
        Customer currentCustomer = service.getCustomerById(id);
        model.addAttribute("customer",currentCustomer);
        return "admin/Customer/update";
    }
    @PostMapping("/update")
    public String updateCustomer(@Valid @ModelAttribute Customer updateCustomer, BindingResult result){
        if(result.hasErrors()){
            return "admin/Customer/update";
        }
        service.updateCustomer(updateCustomer);
        return "redirect:/customers";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id){
        service.deleteCustomerById(id);
        return "redirect:/customers";
    }

}
