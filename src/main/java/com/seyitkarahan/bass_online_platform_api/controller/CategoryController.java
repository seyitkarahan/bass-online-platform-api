package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.request.CategoryCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.request.CategoryUpdateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.CategoryResponse;
import com.seyitkarahan.bass_online_platform_api.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public void create(@RequestBody CategoryCreateRequest request) {
        categoryService.createCategory(request);
    }

    @GetMapping
    public List<CategoryResponse> getAll() {
        return categoryService.getAllCategories();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @RequestBody CategoryUpdateRequest request
    ) {
        categoryService.updateCategory(id, request);
    }
}
