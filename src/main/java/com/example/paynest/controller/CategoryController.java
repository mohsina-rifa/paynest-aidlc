package com.example.paynest.controller;

import com.example.paynest.model.Category;
import com.example.paynest.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //    CRUD : create
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Category category) {
        categoryService.create(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //    CRUD : read
    @GetMapping("/{id}")
    public Category findById(@PathVariable Integer id) {
        return categoryService.findById(id);
    }

    //    CRUD : update
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateById(@PathVariable Integer id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateById(id, category));
    }

    //    CRUD : delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        categoryService.deleteById(id);
    }
}
