package com.Projeto.Fenix.controllers;

import com.Projeto.Fenix.domain.items.Category;
import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.dtos.CategoriesDTO;
import com.Projeto.Fenix.dtos.ItemsDTO;
import com.Projeto.Fenix.services.CategoriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    CategoriesService categoriesService;

    @PostMapping
    public ResponseEntity<Category> addNewItem(@RequestBody CategoriesDTO categoriesDTO) throws Exception {
        Category newCategory = categoriesService.addNewCategory(categoriesDTO.requester(), categoriesDTO.categoryName(), categoriesDTO.categoryDescription(),categoriesDTO.categoryIcon());
        return new ResponseEntity<Category>(newCategory, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Category> updateItemByName(@RequestBody CategoriesDTO categoriesDTO) throws Exception{
        Category updatedCategory = categoriesService.updateCategoryById(categoriesDTO.requester(), categoriesDTO.categoryId(), categoriesDTO.categoryName(), categoriesDTO.categoryDescription(), categoriesDTO.categoryIcon());
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

    @GetMapping("/name/{itemName}")
    public ResponseEntity<Category> listItemByName(@PathVariable String itemName) throws Exception{
        Category getCategory = categoriesService.findCategoryByName(itemName);
        return new ResponseEntity<Category>(getCategory, HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Category> deleteItemById(@RequestBody CategoriesDTO categoriesDTO) throws Exception{
        categoriesService.deleteCategoryById(categoriesDTO.categoryId(), categoriesDTO.requester());
        return new ResponseEntity<Category>(HttpStatus.OK);
    }

    @DeleteMapping("/name/{itemName}")
    public ResponseEntity<Category> deleteItemByName(@PathVariable String categoryName, @RequestBody CategoriesDTO categoriesDTO) throws Exception {
        categoriesService.deleteCategoryByName(categoryName, categoriesDTO.requester());
        return new ResponseEntity<Category>(HttpStatus.OK);
    }
}
