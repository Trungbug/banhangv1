package com.example.ShopWeb.service;

import com.example.ShopWeb.DTO.CategoryDTO;
import com.example.ShopWeb.Model.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(long categoryId, CategoryDTO category);
    void deleteCategory(long id);
}
