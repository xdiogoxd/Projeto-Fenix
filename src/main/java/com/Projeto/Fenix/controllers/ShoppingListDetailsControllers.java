package com.Projeto.Fenix.controllers;

import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.domain.shoppingList.ListMemberRoles;
import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.domain.shoppingList.ShoppingListDetails;
import com.Projeto.Fenix.domain.shoppingList.ShoppingListDetailsView;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.dtos.ShoppingListDetailsDTO;
import com.Projeto.Fenix.services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingList/items")
public class ShoppingListDetailsControllers {

    @Autowired
    UserService userService;

    @Autowired
    ShoppingListDetailsService shoppingListDetailsService;

    @Autowired
    ShoppingListService shoppingListService;

    @Autowired
    ShoppingListMembersService shoppingListMembersService;

    @Autowired
    ItemsService itemsService;

    @PostMapping()
    public ResponseEntity<ShoppingListDetails> addNewItem(HttpServletRequest request, @RequestBody ShoppingListDetailsDTO shoppingListDetailsDTO) throws Exception {

        User requester = userService.findUserByToken(request);

        ShoppingList theList = shoppingListService.findShoppingListById(shoppingListDetailsDTO.listId());
        Item theItem = itemsService.findItemById(shoppingListDetailsDTO.itemId());

        shoppingListMembersService.validateUserAuthorization(requester, theList, ListMemberRoles.CO_ADMIN);

        ShoppingListDetails newShoppingListDetails = shoppingListDetailsService.addItemToList(theList,
                theItem, shoppingListDetailsDTO.quantity());
        return new ResponseEntity<>(newShoppingListDetails, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ShoppingListDetailsView>> listAllItemsByList(HttpServletRequest request, @RequestBody
                                                                        ShoppingListDetailsDTO shoppingListDetailsDTO) throws Exception {

        User requester = userService.findUserByToken(request);

        ShoppingList theList = shoppingListService.findShoppingListById(shoppingListDetailsDTO.listId());

        shoppingListMembersService.validateUserAuthorization(requester,theList, ListMemberRoles.VISITOR);

        List<ShoppingListDetailsView> allItemsByListing = shoppingListDetailsService.listAllItemsByListId(theList);

        return new ResponseEntity<>(allItemsByListing, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> updateItem(HttpServletRequest request, @RequestBody ShoppingListDetailsDTO shoppingListDetailsDTO) throws Exception {
        User requester = userService.findUserByToken(request);

        ShoppingList theList = shoppingListService.findShoppingListById(shoppingListDetailsDTO.listId());
        Item theItem = itemsService.findItemById(shoppingListDetailsDTO.itemId());

        shoppingListMembersService.validateUserAuthorization(requester, theList, ListMemberRoles.CO_ADMIN);

        shoppingListDetailsService.deleItemFromList(theItem, theList, shoppingListDetailsDTO.quantity());

        return new ResponseEntity<>("Item removido", HttpStatus.OK);
    }

}
