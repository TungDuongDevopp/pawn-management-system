package com.tungduong.pawnmanagementsystem.controller.Admin;

import com.tungduong.pawnmanagementsystem.model.Category;
import com.tungduong.pawnmanagementsystem.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/categories")
public class AdminCategoryController {
    private final CategoryService service;

    @GetMapping
    public String getCategory(Model model){
        List<Category> categories = service.getAllCategory();
        model.addAttribute("categories", categories);
        return "admin/Category/index";
    }

    @GetMapping("/create")
    public String createCategory(Model model){
        model.addAttribute("category",new Category());
        return "admin/Category/create";
    }

    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute Category category, BindingResult result){
        if(result.hasErrors()){
            return "admin/Category/create";
        }
        service.saveCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/{id}")
    public String updateCategory(Model model, @PathVariable Long id){
        Category currentCategory = service.getCategoryById(id);
        model.addAttribute("category",currentCategory);
        return "admin/Category/update";
    }
    @PostMapping("/update")
    public String updateCategory(@Valid @ModelAttribute Category updateCategory, BindingResult result){
        if(result.hasErrors()){
            return "admin/Category/update";
        }
        service.updateCategory(updateCategory);
        return "redirect:/categories";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id){
        service.deleteCategoryById(id);
        return "redirect:/categories";
    }
}
