package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.domain.shoppingList.ListMembers;
import com.Projeto.Fenix.repositories.ItemsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemsService {

    @Autowired
    UuidService uuidService;

    @Autowired
    ItemsRepository itemsRepository;

    @Autowired
    EntityManager entityManager;
    Item findItemById(UUID theItemId) throws Exception {
        TypedQuery<Item> theQuery = entityManager.createQuery(
                "FROM Item WHERE itemId=:theId", Item.class);

        theQuery.setParameter("theId",theItemId);

        Item theItem = theQuery.getSingleResult();

        if (theItem.equals(null)){
            throw new Exception("Item não encontrado");
        }else {
            return theItem;
        }


    }

    Item findItemByName(String theItemName) throws Exception {
        TypedQuery<Item> theQuery = entityManager.createQuery(
                "FROM Item WHERE itemName=:theName", Item.class);

        theQuery.setParameter("theName",theItemName);

        Item theItem = theQuery.getSingleResult();

        if (theItem.equals(null)){
            throw new Exception("Item não encontrado");
        }else {
            return theItem;
        }


    }

    Boolean validateNameAvability(String theName) throws Exception {
        Item result = findItemByName(theName);

        if (result.equals(null)){
            return false;
        }else{
            return true;
        }
    }

    Item addNewItem(String itemName, String itemDescription) throws Exception {
        if(validateNameAvability(itemName)){
            UUID theId = uuidService.generateUUID();

            Item theItem = new Item();

            theItem.setItemId(theId);
            theItem.setItemName(itemName);
            theItem.setItemDescription(itemDescription);

            itemsRepository.save(theItem);

            return theItem;
        }
        throw new Exception("Nome duplicado");
    }
}

