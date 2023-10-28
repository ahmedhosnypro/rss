package com.rss.service;


import com.rss.model.Category;
import com.rss.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void save(String name) {
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
    }


    public Boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findOrCreate(String name) {
        var category = categoryRepository.findByName(name).orElse(null);
        if (category == null) {
            category = new Category();
            category.setName(name);
            categoryRepository.save(category);
        }
        return category;
    }

    public Category findByName(String name) {
        var category = categoryRepository.findByName(name).orElse(null);
        if (category == null) {
            throw new EntityNotFoundException("Category:" + name +  " not found");
        }
        return category;
    }
}

