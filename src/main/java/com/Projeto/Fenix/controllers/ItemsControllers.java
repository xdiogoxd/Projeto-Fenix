package com.Projeto.Fenix.controllers;

import com.Projeto.Fenix.domain.items.Category;
import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.dtos.ItemsDTO;
import com.Projeto.Fenix.services.CategoriesService;
import com.Projeto.Fenix.services.ItemsService;
import com.Projeto.Fenix.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Items")
public class ItemsControllers {

    @Autowired
    ItemsService itemsService;

    @Autowired
    CategoriesService categoriesService;

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Item> addNewItem(HttpServletRequest request, @RequestBody ItemsDTO itemsDTO) throws Exception {
        User theUser = userService.findUserByToken(request);
        Category theItemCategory = categoriesService.findCategoryByName(itemsDTO.itemCategory());

        userService.validateUserAuthorization(theUser);

        Item newItem = itemsService.addNewItem(itemsDTO.itemName(), itemsDTO.itemDescription(), theItemCategory);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @PutMapping("/name")
    public ResponseEntity<Item> updateItemByName(HttpServletRequest request, @RequestBody ItemsDTO itemsDTO) throws Exception{
        User theUser = userService.findUserByToken(request);
        Category theItemCategory = categoriesService.findCategoryByName(itemsDTO.itemCategory());

        userService.validateUserAuthorization(theUser);

        Item updatedItem = itemsService.updateItemByName(itemsDTO.itemName(), itemsDTO.itemDescription(), theItemCategory, itemsDTO.itemImage(), itemsDTO.itemBrand());
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @PutMapping("/id")
    public ResponseEntity<Item> updateItemById(HttpServletRequest request, @RequestBody ItemsDTO itemsDTO) throws Exception{
        User theUser = userService.findUserByToken(request);
        Category theItemCategory = categoriesService.findCategoryByName(itemsDTO.itemCategory());

        userService.validateUserAuthorization(theUser);

        Item updatedItem = itemsService.updateItemById(itemsDTO.itemId(), itemsDTO.itemName(), itemsDTO.itemDescription(), itemsDTO.itemImage(), itemsDTO.itemBrand(), theItemCategory);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Item>> listAllItems() throws Exception{
        List<Item> getAllItems = itemsService.listAllItems();
        return new ResponseEntity<>(getAllItems, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Item> listItemById(@RequestBody ItemsDTO itemsDTO) throws Exception{
        Item getItem = itemsService.findItemById(itemsDTO.itemId());
        return new ResponseEntity<>(getItem, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<Item> listItemByName(@RequestBody ItemsDTO itemsDTO) throws Exception{
        Item getItem = itemsService.findItemByName(itemsDTO.itemName());
        return new ResponseEntity<>(getItem, HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public ResponseEntity<String> deleteItemById(HttpServletRequest request, @RequestBody ItemsDTO itemsDTO) throws Exception{
        User theUser = userService.findUserByToken(request);

        userService.validateUserAuthorization(theUser);

        itemsService.deleteItemById(itemsDTO.itemId());
        return new ResponseEntity<>("Item deletado",HttpStatus.OK);
    }

    @DeleteMapping("/name")
    public ResponseEntity<String> deleteItemByName(HttpServletRequest request, @RequestBody ItemsDTO itemsDTO) throws Exception {
        User theUser = userService.findUserByToken(request);

        userService.validateUserAuthorization(theUser);

        itemsService.deleteItemByName(itemsDTO.itemName());
        return new ResponseEntity<>("Item deletado",HttpStatus.OK);
    }
}
