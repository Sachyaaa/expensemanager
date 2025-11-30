package com.sachin.expensemanager.service;

import com.sachin.expensemanager.dto.category.CategoryRequest;
import com.sachin.expensemanager.dto.category.CategoryResponse;
import com.sachin.expensemanager.exception.DuplicateResourceException;
import com.sachin.expensemanager.exception.ResourceNotFoundException;
import com.sachin.expensemanager.model.Category;
import com.sachin.expensemanager.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Category already exists with name: "+ request.getName());
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        categoryRepository.save(category);

        return mapToResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));

        return mapToResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        categoryRepository.save(category);

        return mapToResponse(category);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category with id " + id + " not found");
        }

        categoryRepository.deleteById(id);
    }

    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();

        response.setId(category.getId());
        response.setDescription(category.getDescription());
        response.setName(category.getName());

        return response;
    }
}
