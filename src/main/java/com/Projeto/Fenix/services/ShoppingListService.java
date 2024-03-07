package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.domain.shoppingList.ListMemberRoles;
import com.Projeto.Fenix.domain.shoppingList.ListMembers;
import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.domain.shoppingList.ShoppingListDetails;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.exceptions.ListNotFound;
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


    public List<ShoppingList> listAllShoppingListsByUser(User theUser) throws Exception {
        //Lista todas as listas que esse usuário é membro
        List<ListMembers> theList = shoppingListMembersService.listAllListsByMembers(theUser);
        List<ShoppingList> allShoppingLists = new ArrayList<>();
        if (allShoppingLists.size() == 0){
            throw new ListNotFound();
        }
        for (int i=0; theList.size() < i; i++){
            TypedQuery<ShoppingList> theQuery = entityManager.createQuery(
                    "FROM ShoppingList WHERE listId =:theData", ShoppingList.class);

            theQuery.setParameter("theData",theList.get(i).getListId());

            allShoppingLists.add(theQuery.getSingleResult());
        }
        return allShoppingLists;

    }

    public ShoppingList updateShoppingListById(User theUser, UUID shoppingListId, String shoppingListName,
                                               String shoppingDescription, String shoppingListImage,
                                               Date shoppingListCreationDate, Date shoppingListGoalDate) throws Exception {
        // Valida se o usuário é admin ou coadmin para realizar a atualização
        shoppingListMembersService.validateUserAuthorization(theUser, shoppingListId, ListMemberRoles.ADMIN, ListMemberRoles.CO_ADMIN);

        // Localiza a shopping list, seta os campos e atualiza
        ShoppingList theUpdateList = findShoppingListById(theUser, shoppingListId);

        theUpdateList.setListName(shoppingListName);
        theUpdateList.setListDescription(shoppingDescription);
        theUpdateList.setListImage(shoppingListImage);
        theUpdateList.setCreationDate(shoppingListCreationDate);
        theUpdateList.setGoalDate(shoppingListGoalDate);

        entityManager.merge(theUpdateList);

        return theUpdateList;
    }

    public void deleteShoppingListById(User theUser, UUID shoppingListId) throws Exception {
        // Valida se o usuário é admin para realizar a deleção
        shoppingListMembersService.validateUserAuthorization(theUser, shoppingListId, ListMemberRoles.ADMIN, ListMemberRoles.ADMIN);

        ShoppingList theList = findShoppingListById(theUser, shoppingListId);

        entityManager.remove(theList);
    }


}
