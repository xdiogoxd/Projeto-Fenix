package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.domain.shoppingList.ListMembers;
import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.domain.shoppingList.ShoppingListDetails;
import com.Projeto.Fenix.domain.users.Users;
import com.Projeto.Fenix.repositories.ListMembersRepository;
import com.Projeto.Fenix.repositories.ShoppingListDetailsRepository;
import com.Projeto.Fenix.repositories.ShoppingListRepository;
import org.springframework.stereotype.Service;

@Service
public class ShoppingListService {

    ShoppingListRepository shoppingListRepository;

    ShoppingListDetailsRepository shoppingListDetailsRepository;

    ListMembersRepository listMembersRepository;

    UuidService uuidService;

    ShoppingList createShoppingList(Users owner, String listName){
        ShoppingList newShoppingList = null;

        String theListId = uuidService.generateUUID().toString();
        newShoppingList.setListId(theListId);
        newShoppingList.setListName(listName);
        shoppingListRepository.save(newShoppingList);

        //Cria listMember na tabela ListMembers
        addNewListMember(owner,"Admin", newShoppingList);
        return shoppingListRepository.findById(newShoppingList.getListId();
    }

    void addNewListMember(Users member, String role,ShoppingList theShoppingList){
        ListMembers  newListMember = null;
        String theMemberListId = uuidService.generateUUID().toString();

        newListMember.setListMembersId(theMemberListId);
        newListMember.setMemberId(member);
        newListMember.setListId(theShoppingList);
        newListMember.setListRole(role);

        listMembersRepository.save(newListMember);
    }

    void addItemToList(Users requester, ShoppingList shoppingList, Item theItem, double quantity){
        if((shoppingListDetailsRepository.findById(theItem.getItemId())).isEmpty()) {
            ShoppingListDetails newItem = null;
            newItem.setListId(shoppingList);
            newItem.setItemId(theItem);
            newItem.setItemQuantity(quantity);

            shoppingListDetailsRepository.save(newItem);

        }else{

        }

    }

    void removeItemToList(Users requester, String listId, Item theItem, double quantity){
        //shoppingListDetailsRepository.addItem(requester.getUserId(), listId, theItem.getItemId(), quantity);
    }
}
