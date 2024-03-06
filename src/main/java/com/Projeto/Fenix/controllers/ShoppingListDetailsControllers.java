package com.Projeto.Fenix.controllers;

import com.Projeto.Fenix.domain.shoppingList.ShoppingListDetails;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.dtos.ShoppingListDetailsDTO;
import com.Projeto.Fenix.services.ShoppingListDetailsService;
import com.Projeto.Fenix.services.UserService;
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

    ShoppingListDetailsService shoppingListDetailsService;

    @PostMapping()
    public ResponseEntity<ShoppingListDetails> addNewItem(HttpServletRequest request, @RequestBody ShoppingListDetailsDTO shoppingListDetailsDTO) throws Exception {

        User requester = userService.findUserByToken(request);

        ShoppingListDetails newShoppingListDetails = shoppingListDetailsService.addItemToList(requester, shoppingListDetailsDTO.itemId(),
                shoppingListDetailsDTO.listId(), shoppingListDetailsDTO.quantity());
        return new ResponseEntity<>(newShoppingListDetails, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ShoppingListDetails>> listAllItemsByList(HttpServletRequest request, @RequestBody
                                                                        ShoppingListDetailsDTO shoppingListDetailsDTO){

        User requester = userService.findUserByToken(request);

        List<ShoppingListDetails> allItemsByListing = shoppingListDetailsService.listAllItemsById(requester, shoppingListDetailsDTO.listId());

        return new ResponseEntity<>(allItemsByListing, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> updateItem(HttpServletRequest request, @RequestBody ShoppingListDetailsDTO shoppingListDetailsDTO){
        User requester = userService.findUserByToken(request);


        shoppingListDetailsService.deleItemFromList(requester, shoppingListDetailsDTO.itemId(),
                shoppingListDetailsDTO.listId(), shoppingListDetailsDTO.quantity());

        return new ResponseEntity<>("Item removido", HttpStatus.OK);
    }

}
