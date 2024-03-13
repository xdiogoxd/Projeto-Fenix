package com.Projeto.Fenix.controllers;

import com.Projeto.Fenix.domain.shoppingList.ListMemberRoles;
import com.Projeto.Fenix.domain.shoppingList.ListMembers;
import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.dtos.ShoppingListDTO;
import com.Projeto.Fenix.services.ShoppingListMembersService;
import com.Projeto.Fenix.services.ShoppingListService;
import com.Projeto.Fenix.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingList")
public class ShoppingListControllers {

    @Autowired
    ShoppingListService shoppingListService;

    @Autowired
    UserService  userService;

    @Autowired
    ShoppingListMembersService shoppingListMembersService;

    @PostMapping
    public ResponseEntity<ShoppingList> createShoppingList(HttpServletRequest request,
                                                           @RequestBody ShoppingListDTO shoppingListDTO) throws Exception {
        User theUser = userService.findUserByToken(request);

        ShoppingList newShoppingList = shoppingListService.createShoppingList(theUser, shoppingListDTO.listName());
        return new ResponseEntity<>(newShoppingList, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ShoppingList>> listAllShoppingListsByUser(HttpServletRequest request) throws Exception {
        User theUser = userService.findUserByToken(request);

        List<ListMembers> theListMembers = shoppingListMembersService.listAllListsByMembers(theUser);

        List<ShoppingList> theList = shoppingListService.listAllShoppingListsByUser(theListMembers);
        return new ResponseEntity<>(theList, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<ShoppingList> listShoppingListById(HttpServletRequest request,
                                                             @RequestBody ShoppingListDTO shoppingListDTO) throws Exception {
        User theUser = userService.findUserByToken(request);

        ShoppingList theList = shoppingListService.findShoppingListById(shoppingListDTO.listId());

        shoppingListMembersService.validateUserAuthorization(theUser, theList, ListMemberRoles.VISITOR);

        return new ResponseEntity<>(theList,HttpStatus.OK);
    }

    @PutMapping("/id")
    public ResponseEntity<ShoppingList> updateShoppingListById(HttpServletRequest request,
                                                               @RequestBody ShoppingListDTO shoppingListDTO) throws Exception {
        User theUser = userService.findUserByToken(request);

        ShoppingList theList = shoppingListService.findShoppingListById(shoppingListDTO.listId());

        shoppingListMembersService.validateUserAuthorization(theUser, theList, ListMemberRoles.CO_ADMIN);

        ShoppingList theShoppingList = shoppingListService.updateShoppingListById(shoppingListDTO.listId(),
                shoppingListDTO.listName(), shoppingListDTO.listDescription(), shoppingListDTO.listImage(),
                shoppingListDTO.creationDate(), shoppingListDTO.goalDate());

        return new ResponseEntity<>(theShoppingList, HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public ResponseEntity<String> deleteShoppingListById(HttpServletRequest request,
                                                             @RequestBody ShoppingListDTO shoppingListDTO) throws Exception {
        User theUser = userService.findUserByToken(request);

        ShoppingList theList = shoppingListService.findShoppingListById(shoppingListDTO.listId());

        shoppingListMembersService.validateUserAuthorization(theUser, theList, ListMemberRoles.ADMIN);

        shoppingListService.deleteShoppingListById(theList);
        return new ResponseEntity<>("ShoppingList deleted",HttpStatus.OK);
    }



}
