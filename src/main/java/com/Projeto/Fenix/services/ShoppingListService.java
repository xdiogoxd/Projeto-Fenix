package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.domain.users.Users;
import com.Projeto.Fenix.repositories.ShoppingListDetailsRepository;
import com.Projeto.Fenix.repositories.ShoppingListRepository;
import org.springframework.stereotype.Service;

@Service
public class ShoppingListService {

    ShoppingListRepository shoppingListRepository;

    ShoppingListDetailsRepository shoppingListDetailsRepository;

    ShoppingList createShoppingList(Users owner, String listName){
        return shoppingListRepository.createShoppingList(owner, listName);
    }

    void addItemToList(Users requester, String listId, Item theItem, double quantity){
        if((shoppingListDetailsRepository.findById(theItem.getItemId())).isEmpty()) {
            shoppingListDetailsRepository.addItem(requester.getUserId(), listId, theItem.getItemId(), quantity);
        }else{

        }

    }

    void removeItemToList(Users requester, String listId, Item theItem, double quantity){
        shoppingListDetailsRepository.addItem(requester.getUserId(), listId, theItem.getItemId(), quantity);
    }
}
