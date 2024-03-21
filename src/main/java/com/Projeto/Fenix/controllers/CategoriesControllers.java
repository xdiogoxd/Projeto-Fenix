package com.Projeto.Fenix.controllers;

import com.Projeto.Fenix.domain.items.Category;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.dtos.CategoriesDTO;
import com.Projeto.Fenix.services.CategoriesService;
import com.Projeto.Fenix.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    UserService userService;


    @PostMapping
    public ResponseEntity<Category> addNewItem(HttpServletRequest request, @RequestBody CategoriesDTO categoriesDTO) throws Exception {
        User theUser = userService.findUserByToken(request);

        userService.validateUserAuthorization(theUser);

        Category newCategory = categoriesService.addNewCategory(categoriesDTO.categoryName(),
                categoriesDTO.categoryDescription(),categoriesDTO.categoryIcon());
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @PutMapping("/name")
    public ResponseEntity<Category> updateItemByName(HttpServletRequest request,@RequestBody CategoriesDTO categoriesDTO) throws Exception{
        User theUser = userService.findUserByToken(request);

        userService.validateUserAuthorization(theUser);

        Category updatedCategory = categoriesService.updateCategoryByName(categoriesDTO.categoryName(),
                categoriesDTO.categoryDescription(), categoriesDTO.categoryIcon());
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @PutMapping("/id")
    public ResponseEntity<Category> updateItemById(HttpServletRequest request,@RequestBody CategoriesDTO categoriesDTO) throws Exception{
        User theUser = userService.findUserByToken(request);

        userService.validateUserAuthorization(theUser);

        Category updatedCategory = categoriesService.updateCategoryById(
                categoriesDTO.categoryId(), categoriesDTO.categoryName(), categoriesDTO.categoryDescription(),
                categoriesDTO.categoryIcon());
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Category>> listAllCategories() throws Exception{
        List<Category> getAllCategories = categoriesService.listAllCategories();
        return new ResponseEntity<>(getAllCategories, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Category> listCategoryById(@RequestBody CategoriesDTO categoriesDTO) throws Exception{
        Category getCategory = categoriesService.findCategoryById(categoriesDTO.categoryId());
        return new ResponseEntity<>(getCategory, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<Category> listCategoryByName(@RequestBody CategoriesDTO categoriesDTO) throws Exception{
        Category getCategory = categoriesService.findCategoryByName(categoriesDTO.categoryName());
        return new ResponseEntity<>(getCategory, HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public ResponseEntity<String> deleteCategoryById(HttpServletRequest request, @RequestBody CategoriesDTO categoriesDTO) throws Exception{
        User theUser = userService.findUserByToken(request);

        userService.validateUserAuthorization(theUser);

        categoriesService.deleteCategoryById(categoriesDTO.categoryId());
        return new ResponseEntity<String>("Categoria deletada",HttpStatus.OK);
    }

    @DeleteMapping("/name")
    public ResponseEntity<String> deleteCategoryByName(HttpServletRequest request, @RequestBody CategoriesDTO categoriesDTO) throws Exception {
        User theUser = userService.findUserByToken(request);

        userService.validateUserAuthorization(theUser);

        categoriesService.deleteCategoryByName(categoriesDTO.categoryName());
        return new ResponseEntity<String>("Categoria deletada",HttpStatus.OK);
    }
}
