package com.rss.controller;

import com.rss.model.Category;
import com.rss.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * CategoryController
 * <p>
 *     <a href ="">/get /api/v1/categories - get available categories</a>
 */
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllCategories() {
        var categories = categoryService.findAll().stream().map(Category::getName).toList();
        return ResponseEntity.ok(categories);
    }
}
