package com.tungduong.pawnmanagementsystem.controller;

import com.tungduong.pawnmanagementsystem.model.Category;
import com.tungduong.pawnmanagementsystem.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public String getCategory(Model model){
        List<Category> categories = service.getAllCategory();
        model.addAttribute("categories", categories);
        return "Admin/Category/index";
    }

    @GetMapping("/create")
    public String createCategory(Model model){
        model.addAttribute("category",new Category());
        return "Admin/Category/create";
    }

    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute Category category, BindingResult result){
        if(result.hasErrors()){
            return "Admin/Category/create";
        }
        service.saveCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/{id}")
    public String updateCategory(Model model, @PathVariable Long id){
        Category currentCategory = service.getCategoryById(id).orElse(null);
        model.addAttribute("category",currentCategory);
        return "Admin/Category/update";
    }
    @PostMapping("/update")
    public String updateCategory(@Valid @ModelAttribute Category updateCategory, BindingResult result){
        if(result.hasErrors()){
            return "Admin/Category/update";
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
