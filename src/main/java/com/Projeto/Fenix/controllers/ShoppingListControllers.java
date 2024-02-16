package com.Projeto.Fenix.controllers;

import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.dtos.ShoppingListDTO;
import com.Projeto.Fenix.services.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoppingList")
public class ShoppingListControllers {

    @Autowired
    ShoppingListService shoppingListService;
    @PostMapping
    public ResponseEntity<ShoppingList> createShoppingList(@RequestBody ShoppingListDTO  shoppingListDTO) throws Exception {
        ShoppingList newShoppingList = shoppingListService.createShoppingList(shoppingListDTO.owner(), shoppingListDTO.shoppingListName());
        return new  ResponseEntity<ShoppingList>(newShoppingList, HttpStatus.OK);
    }

}
