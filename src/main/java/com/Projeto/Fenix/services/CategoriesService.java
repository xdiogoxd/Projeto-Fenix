package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Category;

import java.util.List;
import java.util.UUID;

public class CategoriesService {
    public Category addNewCategory(UUID requester, String categoryName, String categoryDescription, String categoryIcon) {
    }

    public Category updateCategoryById(UUID requester, UUID categoryId, String categoryName, String categoryDescription, String categoryIcon) {
    }

    public List<Category> listAllCategories() {
    }

    public Category findCategoryById(UUID categoryId) {
    }

    public Category findCategoryByName(String itemName) {
    }

    public void deleteCategoryById(UUID categoryId, UUID requester) {
    }

    public void deleteCategoryByName(String categoryName, UUID requester) {
    }
}
