package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.domain.shoppingList.ListMemberRoles;
import com.Projeto.Fenix.domain.shoppingList.ListMembers;
import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.domain.shoppingList.ShoppingListDetails;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.repositories.ListMembersRepository;
import com.Projeto.Fenix.repositories.ShoppingListDetailsRepository;
import com.Projeto.Fenix.repositories.ShoppingListRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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
    private ItemsService itemsService;
    @Autowired
    private UuidService uuidService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    ShoppingListMembersService shoppingListMembersService;



    public ShoppingList createShoppingList(User theUser, String listName) throws Exception {
        ShoppingList newShoppingList = new ShoppingList();

        UUID theListId = uuidService.generateUUID();
        Date today = new Date();

        newShoppingList.setListId(theListId);
        newShoppingList.setListName(listName);
        newShoppingList.setCreationDate(today);

        System.out.println(newShoppingList);

        shoppingListRepository.save(newShoppingList);

        //Cria listMember na tabela ListMembers
        shoppingListMembersService.addListMemberOwner(theUser,ListMemberRoles.ADMIN, newShoppingList);
        return newShoppingList;
    }



    ShoppingList findShoppingListById(UUID theShoppingListId) throws Exception {

        TypedQuery<ShoppingList> theQuery = entityManager.createQuery(
                "FROM ShoppingList WHERE listId=:theList", ShoppingList.class);

        theQuery.setParameter("theList",theShoppingListId);

        ShoppingList theList = theQuery.getSingleResult();

        if (theList.equals(null)){
            throw new Exception("Lista não localizada");
        }else {
            return theList;
        }

    }


    public List<ShoppingList> listAllShoppingListsByUser(User theUser) {
    }

    public ShoppingList findListById(User theUser, String shoppingListId) {
    }

    public ShoppingList updateShoppingListById(User theUser, String shoppingListId, String shoppingListName, String shoppingDescription, String shoppingListImage, Date shoppingListCreationDate, Date shoppingListGoalDate) {
    }

    public void deleteShoppingListById(User theUser, String shoppingListId) {
    }
}
