package com.tungduong.pawnmanagementsystem.service;

import com.tungduong.pawnmanagementsystem.model.Category;
import com.tungduong.pawnmanagementsystem.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAllCategory(){
        return repository.findAll();
    }

    public Optional<Category> getCategoryById(Long id){
        return repository.findById(id);
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
        Optional<Category> opt = getCategoryById(category.getId());
        if(opt.isEmpty()) return  null;
        Category current = opt.get();
        current.setName(category.getName());
        current.setDescription(category.getDescription());
        repository.save(category);
        return current;
    }
}
