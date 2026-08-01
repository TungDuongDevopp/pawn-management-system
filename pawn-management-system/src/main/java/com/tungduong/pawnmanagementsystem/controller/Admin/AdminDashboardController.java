package com.tungduong.pawnmanagementsystem.controller.Admin;

import com.tungduong.pawnmanagementsystem.service.AccountService;
import com.tungduong.pawnmanagementsystem.service.CategoryService;
import com.tungduong.pawnmanagementsystem.service.CollateralService;
import com.tungduong.pawnmanagementsystem.service.CustomerService;
import com.tungduong.pawnmanagementsystem.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    private final AccountService accountService;
    private final CustomerService customerService;
    private final StaffService staffService;
    private final CategoryService categoryService;
    private final CollateralService collateralService;

    public AdminDashboardController(AccountService accountService,
                                    CustomerService customerService,
                                    StaffService staffService,
                                    CategoryService categoryService,
                                    CollateralService collateralService) {
        this.accountService = accountService;
        this.customerService = customerService;
        this.staffService = staffService;
        this.categoryService = categoryService;
        this.collateralService = collateralService;
    }


    @GetMapping("/dashboard")
    public String dashboardAdmin(Model model) {
        model.addAttribute("accountCount", accountService.getAllAccounts().size());
        model.addAttribute("customerCount", customerService.getAllCustomer().size());
        model.addAttribute("staffCount", staffService.getAllStaffs().size());
        model.addAttribute("categoryCount", categoryService.getAllCategory().size());
        model.addAttribute("collateralCount", collateralService.getAllCollateral().size());

        model.addAttribute("recentAccounts", accountService.getAllAccounts());
        model.addAttribute("recentCustomers", customerService.getAllCustomer());
        model.addAttribute("recentCollaterals", collateralService.getAllCollateral());

        return "admin/dashboard";
    }
}

