package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.repositories.ItemsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemsService {

    @Autowired
    UuidService uuidService;

    @Autowired
    UserService userService;

    @Autowired
    ItemsRepository itemsRepository;

    @Autowired
    EntityManager entityManager;
    public Item findItemById(UUID theItemId) throws Exception {
        TypedQuery<Item> theQuery = entityManager.createQuery(
                "FROM Item WHERE itemId=:theId", Item.class);

        theQuery.setParameter("theId",theItemId);


        try {
            return theQuery.getSingleResult();
        }catch (Exception e){
            return null;
        }


    }

    public Item findItemByName(String theItemName) throws Exception {
        TypedQuery<Item> theQuery = entityManager.createQuery(
                "FROM Item WHERE itemName=:theName", Item.class);

        theQuery.setParameter("theName",theItemName);

        try {
            return theQuery.getSingleResult();
        }catch (Exception e){
            return null;
        }

    }

    Boolean validateNameAvailability(String theName) throws Exception {
        Item result = findItemByName(theName);


        if (result == null){
            // nome disponivel
            return true;
        }else{
            // nome indisponivel
            return false;
        }
    }

    public Item addNewItem(UUID requester, String itemName, String itemDescription, String itemCategory) throws Exception {

        if(userService.validateUserAuthorization(requester)){
            if(validateNameAvailability(itemName)){
                UUID theId = uuidService.generateUUID();

                Item theItem = new Item();

                theItem.setItemId(theId);
                theItem.setItemName(itemName);
                theItem.setItemCategory(itemCategory);
                theItem.setItemDescription(itemDescription);

                itemsRepository.save(theItem);

                return theItem;
            }else {
                throw new Exception("Nome duplicado");
            }
        }else {
            throw new Exception("Usuário não autorizado");
        }
    }

    public Item updateItemById(UUID requester, UUID itemId, String itemName, String itemDescription,
                               String itemImage, String itemBrand) throws Exception {
        if(userService.validateUserAuthorization(requester)){
            Item theUpdatedItem = findItemById(itemId);

            theUpdatedItem.setItemName(itemName);
            theUpdatedItem.setItemDescription(itemDescription);
            theUpdatedItem.setItemImage(itemImage);
            theUpdatedItem.setItemBrand(itemBrand);

            entityManager.merge(theUpdatedItem);

            return  theUpdatedItem;
        }else {
            throw new Exception("Usuário não autorizado");
        }
    }

    public List<Item> listAllItems() throws Exception {
        TypedQuery<Item> theQuery = entityManager.createQuery(
                "FROM Item", Item.class);

        List<Item> theItems = theQuery.getResultList();

        if(theItems.size() == 0){
            throw new Exception("Nenhum Item encontrado");
        }
        return theItems;
    }
}

