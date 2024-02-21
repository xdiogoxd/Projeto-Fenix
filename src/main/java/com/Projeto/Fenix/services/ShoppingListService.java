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
import jakarta.persistence.TypedQuery;
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
        // checa a role do requester nesta lista, para verificar se o usuário pode realizar a atividade ou não

        if (validateShoppingListToUser(requester, shoppingList)){

            if(validateItemAlreadyExist(theItem, shoppingList) ){


            }

        }


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

    boolean validateShoppingListToUser(Users requester, ShoppingList shoppingList){

        TypedQuery<ListMembers> theQuery = entityManager.createQuery(
                "FROM ListMembers WHERE listId=:theList AND memberId=:theMember ", ListMembers.class);

        theQuery.setParameter("theList",shoppingList.getListId());
        theQuery.setParameter("theMember",requester.getUserId());

        ListMembers theMember = theQuery.getSingleResult();

        if (theMember.getListRole() != "Admin"){
            return false;
        }else {
            return true;
        }

    }

    boolean validateItemAlreadyExist(Item theItem, ShoppingList shoppingList){
        TypedQuery<ShoppingListDetails> theQuery = entityManager.createQuery(
                "FROM ShoppingListDetails WHERE listId=:theList AND itemId=:theItem", ShoppingListDetails.class);

        theQuery.setParameter("theList", shoppingList.getListId());
        theQuery.setParameter("theItem", theItem.getItemId());

        ShoppingListDetails theShoppingListDetails = theQuery.getSingleResult();

        if (theShoppingListDetails.equals(null)){
            return false;
        }else{
            return true;
        }
    }

    String updateItems(ShoppingList theList, Item theItem, double quantity, String action) throws Exception {

        if(validateItemAlreadyExist(theItem, theList)){
            if(action == "add"){
                TypedQuery<ShoppingListDetails> theQuery = entityManager.createQuery(
                        "FROM ShoppingListDetails WHERE listId=:theList AND itemId=:theItem", ShoppingListDetails.class);

                theQuery.setParameter("theList", theList.getListId());
                theQuery.setParameter("theItem", theItem.getItemId());

                ShoppingListDetails theShoppingListDetails = theQuery.getSingleResult();

                theShoppingListDetails.setItemQuantity(theShoppingListDetails.getItemQuantity() + quantity);

                entityManager.merge(theShoppingListDetails);
                return "Item updated";
            }else if (action == "remove"){
                TypedQuery<ShoppingListDetails> theQuery = entityManager.createQuery(
                        "FROM ShoppingListDetails WHERE listId=:theList AND itemId=:theItem", ShoppingListDetails.class);

                theQuery.setParameter("theList", theList.getListId());
                theQuery.setParameter("theItem", theItem.getItemId());

                ShoppingListDetails theShoppingListDetails = theQuery.getSingleResult();

                if (theShoppingListDetails.getItemQuantity() < quantity){
                    entityManager.remove(theShoppingListDetails);
                    return "Item removed";
                }
                theShoppingListDetails.setItemQuantity(theShoppingListDetails.getItemQuantity() - quantity);

                entityManager.merge(theShoppingListDetails);

            } else {
                throw new Exception("Ação não suportada");
            }
        }
        throw new Exception("Item não está na lista");
    }
}
