package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.items.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, String> {

    Optional<Category> createCategory(Category newCategory);

    Optional<Category> updateCategoryById(String theId,Category updatedCategory);

    void deleteCategoryById(String id);

    List<Category> listAllCategories();


}
