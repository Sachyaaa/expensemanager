package com.sachin.expensemanager.service;

import com.sachin.expensemanager.dto.category.CategoryRequest;
import com.sachin.expensemanager.dto.category.CategoryResponse;
import com.sachin.expensemanager.exception.ResourceNotFoundException;
import com.sachin.expensemanager.model.Category;
import com.sachin.expensemanager.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void shouldCreateCategory() {
        CategoryRequest request = new CategoryRequest("Food", "Food related expenses");

        Category savedCategory = Category.builder()
                .id(1L)
                .name("Food")
                .description("Food related expenses")
                .build();

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(savedCategory);

        CategoryResponse response = categoryService.createCategory(request);

        assertNotNull(response);
        assertEquals("Food", response.getName());
        assertEquals("Food related expenses", response.getDescription());

        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void shouldGetAllCategories() {
        Category category1 = Category.builder().id(1L).name("Food").build();
        Category category2 = Category.builder().id(2L).name("Travel").build();

        when(categoryRepository.findAll())
                .thenReturn(List.of(category1, category2));

        List<CategoryResponse> responses = categoryService.getAllCategories();

        assertEquals(2, responses.size());
        assertEquals("Food", responses.get(0).getName());
        assertEquals("Travel", responses.get(1).getName());

        verify(categoryRepository).findAll();
    }

    @Test
    void shouldGetCategoryById() {
        Category category = Category.builder()
                .id(1L)
                .name("Food")
                .description("Food related expenses")
                .build();

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        CategoryResponse response = categoryService.getCategoryById(1L);

        assertNotNull(response);
        assertEquals("Food", response.getName());

        verify(categoryRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.getCategoryById(1L)
        );

        verify(categoryRepository).findById(1L);
    }

    @Test
    void shouldDeleteCategory() {
        Category category = Category.builder().id(1L).build();

        when(categoryRepository.existsById(1L))
                .thenReturn(true);

        categoryService.deleteCategory(1L);

        verify(categoryRepository).existsById(1L);
        verify(categoryRepository).deleteById(1L);
    }
}