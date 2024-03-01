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
    ListMembersService listMembersService;



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
        listMembersService.addListMemberOwner(theUser,ListMemberRoles.ADMIN, newShoppingList);
        return newShoppingList;
    }


    public ShoppingListDetails addItemToList(UUID requesterId, UUID shoppingListId, UUID theItemId, double quantity) throws Exception {
        // checa a role do requester nesta lista, para verificar se o usuário pode realizar a atividade ou não

        User requester = userService.findUserByUserId(requesterId);
        ShoppingList shoppingList = findShoppingListById(shoppingListId);
        Item theItem = itemsService.findItemById(theItemId);

        if (validateShoppingListToUser(requester, shoppingList)){

            if(validateItemAlreadyExist(theItem, shoppingList) ){
                ShoppingListDetails theResult = updateItems(shoppingList, theItem, quantity, "add");
                return theResult;
            }else {
                ShoppingListDetails theResult = addNewItemToList(shoppingList, theItem, quantity);
                return theResult;
            }

        }
        throw new Exception("Usuário não autorizado para realizar atualização da lista");

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

    ShoppingListDetails updateItems(ShoppingList theList, Item theItem, double quantity, String action) throws Exception {

        if(validateItemAlreadyExist(theItem, theList)){
            if(action == "add"){
                TypedQuery<ShoppingListDetails> theQuery = entityManager.createQuery(
                        "FROM ShoppingListDetails WHERE listId=:theList AND itemId=:theItem", ShoppingListDetails.class);

                theQuery.setParameter("theList", theList.getListId());
                theQuery.setParameter("theItem", theItem.getItemId());

                ShoppingListDetails theShoppingListDetails = theQuery.getSingleResult();

                theShoppingListDetails.setItemQuantity(theShoppingListDetails.getItemQuantity() + quantity);

                entityManager.merge(theShoppingListDetails);

                return theShoppingListDetails;

            }else if (action == "remove"){
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
}
