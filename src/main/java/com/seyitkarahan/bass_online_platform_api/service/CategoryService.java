package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.request.CategoryCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.request.CategoryUpdateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.CategoryResponse;
import com.seyitkarahan.bass_online_platform_api.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(CategoryCreateRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category already exists");
        }

        categoryRepository.save(
                request.getName(),
                request.getDescription()
        );
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public void updateCategory(
            Long categoryId,
            CategoryUpdateRequest request
    ) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Category not found");
        }

        if (categoryRepository.existsByNameAndNotId(
                request.getName(),
                categoryId
        )) {
            throw new RuntimeException("Category name already exists");
        }

        categoryRepository.update(
                categoryId,
                request.getName(),
                request.getDescription()
        );
    }
}
