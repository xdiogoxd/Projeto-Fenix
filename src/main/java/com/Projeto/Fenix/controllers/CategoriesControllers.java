package com.Projeto.Fenix.controllers;

import com.Projeto.Fenix.domain.items.Category;
import com.Projeto.Fenix.dtos.CategoriesDTO;
import com.Projeto.Fenix.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesControllers {

    @Autowired
    CategoriesService categoriesService;

    @PostMapping
    public ResponseEntity<Category> addNewItem(@RequestBody CategoriesDTO categoriesDTO) throws Exception {
        Category newCategory = categoriesService.addNewCategory(categoriesDTO.requester(),
                categoriesDTO.categoryName(), categoriesDTO.categoryDescription(),categoriesDTO.categoryIcon());
        return new ResponseEntity<Category>(newCategory, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Category> updateItemByName(@RequestBody CategoriesDTO categoriesDTO) throws Exception{
        Category updatedCategory = categoriesService.updateCategoryById(categoriesDTO.requester(),
                categoriesDTO.categoryId(), categoriesDTO.categoryName(), categoriesDTO.categoryDescription(),
                categoriesDTO.categoryIcon());
        return new ResponseEntity<Category>(updatedCategory, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Category>> listAllItems() throws Exception{
        List<Category> getAllCategories = categoriesService.listAllCategories();
        return new ResponseEntity<List<Category>>(getAllCategories, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Category> listItemById(@RequestBody CategoriesDTO categoriesDTO) throws Exception{
        Category getCategory = categoriesService.findCategoryById(categoriesDTO.categoryId());
        return new ResponseEntity<Category>(getCategory, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<Category> listItemByName(@RequestBody CategoriesDTO categoriesDTO) throws Exception{
        Category getCategory = categoriesService.findCategoryByName(categoriesDTO.categoryName());
        return new ResponseEntity<Category>(getCategory, HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public ResponseEntity<String> deleteItemById(@RequestBody CategoriesDTO categoriesDTO) throws Exception{
        categoriesService.deleteCategoryById(categoriesDTO.categoryId(), categoriesDTO.requester());
        return new ResponseEntity<String>("Categoria deletada",HttpStatus.OK);
    }

    @DeleteMapping("/name")
    public ResponseEntity<String> deleteItemByName(@RequestBody CategoriesDTO categoriesDTO) throws Exception {
        categoriesService.deleteCategoryByName(categoriesDTO.categoryName(), categoriesDTO.requester());
        return new ResponseEntity<String>("Categoria deletada",HttpStatus.OK);
    }
}
