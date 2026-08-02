package com.tungduong.pawnmanagementsystem.service;

import com.tungduong.pawnmanagementsystem.helper.ResourceNotFoundException;
import com.tungduong.pawnmanagementsystem.model.Category;
import com.tungduong.pawnmanagementsystem.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository repository;

    public List<Category> getAllCategory(){
        return repository.findAll();
    }

    public Category getCategoryById(Long id){
        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }

    public Category saveCategory(Category category){
        return repository.save(category);
    }

    public boolean deleteCategoryById(Long id){
        if(!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
    public Category updateCategory(Category category){
        Category current = getCategoryById(category.getId());
        current.setName(category.getName());
        current.setDescription(category.getDescription());
        repository.save(category);
        return current;
    }
}
