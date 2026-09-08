package com.example.paynest.service;

import com.example.paynest.model.Category;
import com.example.paynest.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void create(Category category) {
        categoryRepository.save(category);
    }

    public Category updateById(Integer id, Category category) {
        Category existingCategory = findById(id);
        existingCategory.setName(category.getName());

        return categoryRepository.save(existingCategory);
    }

    public void deleteById(Integer id) {
        Category existingCategory = findById(id);
        categoryRepository.delete(existingCategory);
    }

}
