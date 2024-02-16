package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.domain.shoppingList.ListMembers;
import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.domain.shoppingList.ShoppingListDetails;
import com.Projeto.Fenix.domain.users.Users;
import com.Projeto.Fenix.repositories.ListMembersRepository;
import com.Projeto.Fenix.repositories.ShoppingListDetailsRepository;
import com.Projeto.Fenix.repositories.ShoppingListRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShoppingListService {

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private ShoppingListDetailsRepository shoppingListDetailsRepository;
    @Autowired
    private ListMembersRepository listMembersRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UuidService uuidService;

    @Autowired
    private EntityManager entityManager;

    public ShoppingList createShoppingList(UUID owner, String listName) throws Exception {
        ShoppingList newShoppingList = new ShoppingList();

        UUID theListId = uuidService.generateUUID();
        Date today = new Date();

        newShoppingList.setListId(theListId);
        newShoppingList.setListName(listName);
        newShoppingList.setCreationDate(today);

        System.out.println(newShoppingList);

        shoppingListRepository.save(newShoppingList);

        //Cria listMember na tabela ListMembers
        addNewListMember(owner,"Admin", newShoppingList);
        return newShoppingList;
    }

    void addNewListMember(UUID member, String role,ShoppingList theShoppingList) throws Exception {
        ListMembers newListMember = new ListMembers();
        UUID theMemberListId = uuidService.generateUUID();

        Users listOwner = userService.findUserByUserId(member);

        System.out.println(listOwner);

        newListMember.setListMembersId(theMemberListId);
        newListMember.setListId(theShoppingList);
        newListMember.setMemberId(listOwner);
        newListMember.setListRole(role);

        listMembersRepository.save(newListMember);
    }

    void addItemToList(Users requester, ShoppingList shoppingList, Item theItem, double quantity){
        if((shoppingListDetailsRepository.findById(theItem.getItemId())).isEmpty()) {
            ShoppingListDetails newItem = new ShoppingListDetails();
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
