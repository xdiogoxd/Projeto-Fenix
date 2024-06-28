package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.items.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, UUID> {

    Category findCategoryByCategoryName(String name);

    Category findCategoryByCategoryId(UUID uuid);

    @Query("select * from categories")
    List<Category> findAllCategories();



//    List<Category> listAllCategories();


}
