package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.domain.shoppingList.*;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.repositories.ShoppingListDetailsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShoppingListDetailsService {

    @Autowired
    ShoppingListDetailsRepository shoppingListDetailsRepository;

    @Autowired
    ShoppingListService shoppingListService;

    @Autowired
    ItemsService itemsService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    UuidService uuidService;

    @Autowired
    ShoppingListMembersService shoppingListMembersService;

    public ShoppingListDetails addItemToList(User requester, UUID shoppingListId, UUID theItemId, double quantity) throws Exception {
        // checa a role do requester nesta lista, para verificar se o usuário pode realizar a atividade ou não
        shoppingListMembersService.validateUserAuthorization(requester, shoppingListId, ListMemberRoles.ADMIN, ListMemberRoles.CO_ADMIN);


        ShoppingList shoppingList = shoppingListService.findShoppingListById(requester, shoppingListId);
        Item theItem = itemsService.findItemById(theItemId);

        if (validateShoppingListToUser(requester, shoppingList)){

            if(validateItemAlreadyExist(theItem, shoppingList) ){
                ShoppingListDetails theResult = updateItems(shoppingList, theItem, quantity, EnumShoppingListMethod.ADD);
                return theResult;
            }else {
                ShoppingListDetails theResult = addNewItemToList(shoppingList, theItem, quantity);
                return theResult;
            }

        }
        throw new Exception("Usuário não autorizado para realizar atualização da lista");

    }

    void removeItemToList(User requester, String listId, Item theItem, double quantity){
        //shoppingListDetailsRepository.addItem(requester.getUserId(), listId, theItem.getItemId(), quantity);
    }

    boolean validateShoppingListToUser(User requester, ShoppingList shoppingList){

        TypedQuery<ListMembers> theQuery = entityManager.createQuery(
                "FROM ListMembers WHERE listId=:theList AND memberId=:theMember ", ListMembers.class);

        theQuery.setParameter("theList",shoppingList.getListId());
        theQuery.setParameter("theMember",requester.getUserId());

        ListMembers theMember = theQuery.getSingleResult();

        if (theMember.getListRole() != ListMemberRoles.ADMIN){
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

    ShoppingListDetails updateItems(ShoppingList theList, Item theItem, double quantity, EnumShoppingListMethod action) throws Exception {

        if(validateItemAlreadyExist(theItem, theList)){
            if(action.equals(EnumShoppingListMethod.ADD)){
                TypedQuery<ShoppingListDetails> theQuery = entityManager.createQuery(
                        "FROM ShoppingListDetails WHERE listId=:theList AND itemId=:theItem", ShoppingListDetails.class);

                theQuery.setParameter("theList", theList.getListId());
                theQuery.setParameter("theItem", theItem.getItemId());

                ShoppingListDetails theShoppingListDetails = theQuery.getSingleResult();

                theShoppingListDetails.setItemQuantity(theShoppingListDetails.getItemQuantity() + quantity);

                entityManager.merge(theShoppingListDetails);

                return theShoppingListDetails;

            }else if (action.equals(EnumShoppingListMethod.REMOVE){
                TypedQuery<ShoppingListDetails> theQuery = entityManager.createQuery(
                        "FROM ShoppingListDetails WHERE listId=:theList AND itemId=:theItem", ShoppingListDetails.class);

                theQuery.setParameter("theList", theList.getListId());
                theQuery.setParameter("theItem", theItem.getItemId());

                ShoppingListDetails theShoppingListDetails = theQuery.getSingleResult();

                if (theShoppingListDetails.getItemQuantity() < quantity){
                    entityManager.remove(theShoppingListDetails);
                    return null;
                }
                theShoppingListDetails.setItemQuantity(theShoppingListDetails.getItemQuantity() - quantity);

                entityManager.merge(theShoppingListDetails);
                return theShoppingListDetails;


            } else {
                throw new Exception("Ação não suportada");
            }
        }
        throw new Exception("Item não está na lista");
    }

    ShoppingListDetails addNewItemToList(ShoppingList theList, Item theItem, double quantity) throws Exception {
        if(!validateItemAlreadyExist(theItem, theList)){
            ShoppingListDetails theNewItem = new ShoppingListDetails();

            UUID theDetailsId = uuidService.generateUUID();

            theNewItem.setDetailsId(theDetailsId);
            theNewItem.setItemId(theItem);
            theNewItem.setListId(theList);
            theNewItem.setItemQuantity(quantity);

            ShoppingListDetails theResult = shoppingListDetailsRepository.save(theNewItem);

            return theResult;
        }
        throw new Exception("Item já existe na lista");
    }


    public List<ShoppingListDetails> listAllItemsById(User requester, UUID listId) {
    }

    public void deleItemFromList(User requester, UUID itemId, UUID listId, double quantity) {
    }
}
