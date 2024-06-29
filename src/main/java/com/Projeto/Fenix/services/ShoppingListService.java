package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.domain.shoppingList.ListMemberRoles;
import com.Projeto.Fenix.domain.shoppingList.ListMembers;
import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.domain.shoppingList.ShoppingListDetails;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.exceptions.ListNotFound;
import com.Projeto.Fenix.exceptions.MissingFieldsException;
import com.Projeto.Fenix.exceptions.ShoppingListNotFound;
import com.Projeto.Fenix.exceptions.UserNotAuthorized;
import com.Projeto.Fenix.repositories.ListMembersRepository;
import com.Projeto.Fenix.repositories.ShoppingListDetailsRepository;
import com.Projeto.Fenix.repositories.ShoppingListRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ShoppingListService {

    @Autowired
    ShoppingListRepository shoppingListRepository;

    @Autowired
    UuidService uuidService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ShoppingListMembersService shoppingListMembersService;



    public ShoppingList createShoppingList(User theUser, String listName) throws Exception {
        if (listName != null){
            //Cria nova shopping list e seta os atributos
            ShoppingList newShoppingList = new ShoppingList();

            UUID theListId = uuidService.generateUUID();
            Date today = new Date();

            newShoppingList.setListId(theListId);
            newShoppingList.setListName(listName);
            newShoppingList.setCreationDate(today);

            shoppingListRepository.save(newShoppingList);

            //Cria listMember na tabela ListMembers
            shoppingListMembersService.addListMemberOwner(theUser,ListMemberRoles.ADMIN, newShoppingList);
            return newShoppingList;
        }
        throw new MissingFieldsException();
    }

    public ShoppingList findShoppingListById(UUID theShoppingListId) throws Exception {
        //Cria query para achar a lista
        TypedQuery<ShoppingList> theQuery = entityManager.createQuery(
                "FROM ShoppingList WHERE listId=:theList", ShoppingList.class);

        theQuery.setParameter("theList",theShoppingListId);
        try{
            return theQuery.getSingleResult();
        }catch (Exception exception){
            throw new ShoppingListNotFound();
        }
    }

    public List<ShoppingList> listAllShoppingListsByUser(List<ListMembers> theList) throws Exception {

        List<ShoppingList> allShoppingLists = new ArrayList<>();
        System.out.println(theList);

        if (theList.size() == 0){
            throw new ListNotFound();
        }
        for (int i = 0; i < theList.size(); i++){
            TypedQuery<ShoppingList> theQuery = entityManager.createQuery(
                    "FROM ShoppingList WHERE listId =:theData", ShoppingList.class);

            theQuery.setParameter("theData",theList.get(i).getListId().getListId());

            allShoppingLists.add(theQuery.getSingleResult());
            System.out.println(allShoppingLists);
        }
        return allShoppingLists;
    }

    @Transactional
    public ShoppingList updateShoppingListById(UUID shoppingListId, String shoppingListName,
                                               String shoppingDescription, String shoppingListImage,
                                               Date shoppingListCreationDate, Date shoppingListGoalDate) throws Exception {

        // Localiza a shopping list, seta os campos e atualiza
        ShoppingList theUpdateList = findShoppingListById(shoppingListId);

        theUpdateList.setListName(shoppingListName);
        theUpdateList.setListDescription(shoppingDescription);
        theUpdateList.setListImage(shoppingListImage);
        theUpdateList.setCreationDate(shoppingListCreationDate);
        theUpdateList.setGoalDate(shoppingListGoalDate);

        entityManager.merge(theUpdateList);

        return theUpdateList;
    }

    @Transactional
    public void deleteShoppingListById(ShoppingList theList) throws Exception {

        shoppingListMembersService.deleteAllMembersFromList(theList);
        entityManager.remove(theList);
    }


}
