package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.domain.shoppingList.ListMemberRoles;
import com.Projeto.Fenix.domain.shoppingList.ListMembers;
import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.domain.shoppingList.ShoppingListDetails;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.exceptions.ShoppingListNotFound;
import com.Projeto.Fenix.exceptions.UserNotAuthorized;
import com.Projeto.Fenix.repositories.ListMembersRepository;
import com.Projeto.Fenix.repositories.ShoppingListDetailsRepository;
import com.Projeto.Fenix.repositories.ShoppingListRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



    public ShoppingList findShoppingListById(User requester, UUID theShoppingListId) throws Exception {
        //Cria query para achar a lista
        TypedQuery<ShoppingList> theQuery = entityManager.createQuery(
                "FROM ShoppingList WHERE listId=:theList", ShoppingList.class);

        theQuery.setParameter("theList",theShoppingListId);

        try{
            ShoppingList theList = theQuery.getSingleResult();
            // Valida se o usuário é membro desta lista
            try{
                shoppingListMembersService.findMemberByList(requester.getUserId(),theList.getListId());
            }catch (Exception e){
                throw new UserNotAuthorized();
            }
            // Caso o usuário seja membro retorna a lista
            return theQuery.getSingleResult();
        }catch (Exception exception){
            throw new ShoppingListNotFound();
        }
    }


    public List<ShoppingList> listAllShoppingListsByUser(User theUser) {
        List<ListMembers> theList = shoppingListMembersService.listAllListsByMembers(theUser);
        List<ShoppingList> allShoppingLists = new ArrayList<>();
        for (int i=0; theList.size() < i; i++){
            TypedQuery<ShoppingList> theQuery = entityManager.createQuery(
                    "FROM ShoppingList WHERE listId =:theData", ShoppingList.class);

            theQuery.setParameter("theData",theList.get(i).getListId());

            allShoppingLists.add(theQuery.getSingleResult());
        }

        return allShoppingLists;

    }

    public ShoppingList updateShoppingListById(User theUser, String shoppingListId, String shoppingListName, String shoppingDescription, String shoppingListImage, Date shoppingListCreationDate, Date shoppingListGoalDate) {
    }

    public void deleteShoppingListById(User theUser, String shoppingListId) {
    }
}
