package com.tungduong.pawnmanagementsystem.controller;

import com.tungduong.pawnmanagementsystem.model.Collateral;
import com.tungduong.pawnmanagementsystem.model.enums.CollateralSatus;
import com.tungduong.pawnmanagementsystem.service.CategoryService;
import com.tungduong.pawnmanagementsystem.service.CollateralService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collaterals")
public class CollateralController {
    private final CollateralService service;
    private final CategoryService categoryService;

    public CollateralController(CollateralService service, CategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }
    @GetMapping
    public String getCollateral(Model model){
        List<Collateral> collaterals= service.getAllCollateral();
        model.addAttribute("collaterals", collaterals);
        return "Admin/Collateral/index";
    }

    @GetMapping("/create")
    public String createCollateral(Model model){
        model.addAttribute("collateral",new Collateral());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("statuses", CollateralSatus.values());
        return "Admin/Collateral/create";
    }

    @PostMapping("/create")
    public String createCollateral(@Valid @ModelAttribute Collateral collateral, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("categories", categoryService.getAllCategory());
            model.addAttribute("statuses", CollateralSatus.values());
            return "Admin/Collateral/create";
        }
        service.saveCollateral(collateral);
        return "redirect:/collaterals";
    }

    @GetMapping("/{id}")
    public String updateCollateral(Model model, @PathVariable Long id){
        Collateral currentCollateral = service.getCollateralById(id).orElse(null);
        model.addAttribute("collateral",currentCollateral);
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("statuses", CollateralSatus.values());
        return "Admin/Collateral/update";
    }
    @PostMapping("/update")
    public String updateCollateral(@Valid @ModelAttribute Collateral updateCollateral, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("categories", categoryService.getAllCategory());
            model.addAttribute("statuses", CollateralSatus.values());
            return "Admin/Collateral/update";
        }
        service.updateCollateral(updateCollateral);
        return "redirect:/collaterals";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCollateral(@PathVariable Long id){
        service.deleteCollateralById(id);
        return "redirect:/collaterals";
    }
}
