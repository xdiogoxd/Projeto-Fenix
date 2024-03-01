package com.Projeto.Fenix.controllers;

import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.domain.shoppingList.ShoppingListDetails;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.dtos.ListMembersDTO;
import com.Projeto.Fenix.dtos.ShoppingListDTO;
import com.Projeto.Fenix.dtos.ShoppingListDetailsDTO;
import com.Projeto.Fenix.infra.security.TokenService;
import com.Projeto.Fenix.services.ListMembersService;
import com.Projeto.Fenix.services.ShoppingListService;
import com.Projeto.Fenix.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingList")
public class ShoppingListControllers {

    @Autowired
    ShoppingListService shoppingListService;

    @Autowired
    UserService  userService;

    ListMembersService listMembersService;

    @PostMapping
    public ResponseEntity<ShoppingList> createShoppingList(HttpServletRequest request,
                                                           @RequestBody ShoppingListDTO shoppingListDTO) throws Exception {
        User theUser = userService.findUserByToken(request);

        ShoppingList newShoppingList = shoppingListService.createShoppingList(theUser, shoppingListDTO.shoppingListName());
        return new ResponseEntity<ShoppingList>(newShoppingList, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ShoppingList>> listAllShoppingListsByUser(HttpServletRequest request){
        User theUser = userService.findUserByToken(request);

        List<ShoppingList> theList = shoppingListService.listAllShoppingListsByUser(theUser);
        return new ResponseEntity<List<ShoppingList>>(theList, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<ShoppingList> listShoppingListById(HttpServletRequest request,
                                                             @RequestBody ShoppingListDTO shoppingListDTO){
        User theUser = userService.findUserByToken(request);

        ShoppingList theShoppingList = shoppingListService.findListById(theUser, shoppingListDTO.shoppingListId());
        return new ResponseEntity<ShoppingList>(theShoppingList,HttpStatus.OK);
    }

    @PutMapping("/id")
    public ResponseEntity<ShoppingList> updateShoppingListById(HttpServletRequest request,
                                                               @RequestBody ShoppingListDTO shoppingListDTO){
        User theUser = userService.findUserByToken(request);

        ShoppingList theShoppingList = shoppingListService.updateShoppingListById(theUser,shoppingListDTO.shoppingListId(),
                shoppingListDTO.shoppingListName(), shoppingListDTO.shoppingDescription(),
                shoppingListDTO.shoppingListImage(), shoppingListDTO.shoppingListCreationDate(),
                shoppingListDTO.shoppingListGoalDate());

        return new ResponseEntity<ShoppingList>(theShoppingList, HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public ResponseEntity<String> deleteShoppingListById(HttpServletRequest request,
                                                             @RequestBody ShoppingListDTO shoppingListDTO){
        User theUser = userService.findUserByToken(request);

        shoppingListService.deleteShoppingListById(theUser, shoppingListDTO.shoppingListId());
        return new ResponseEntity<String>("ShoppingList deleted",HttpStatus.OK);
    }

    @PostMapping("/id/members")
    public ResponseEntity<String> addNewListMember(HttpServletRequest request, @RequestBody ListMembersDTO listMembersDTO) throws Exception {

        User theOwner = userService.findUserByToken(request);

        listMembersService.addListMember(theOwner, listMembersDTO.newMember(), listMembersDTO.role(), listMembersDTO.shoppingListId());

    }





    @PostMapping
    @RequestMapping("/item")
    public ResponseEntity<ShoppingListDetails> addNewItem(@RequestBody ShoppingListDetailsDTO shoppingListDetailsDTO) throws Exception {
        ShoppingListDetails newShoppingListDetails = shoppingListService.addItemToList(shoppingListDetailsDTO.requesterId(),
                shoppingListDetailsDTO.itemId(), shoppingListDetailsDTO.itemId(), shoppingListDetailsDTO.quantity());
        return new ResponseEntity<ShoppingListDetails>(newShoppingListDetails,HttpStatus.OK);
    }


}
