package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Category;
import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.exceptions.ItemAlreadyExistException;
import com.Projeto.Fenix.exceptions.ItemNotFoundException;
import com.Projeto.Fenix.exceptions.MissingFieldsException;
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

    public Item addNewItem(String itemName, String itemDescription, Category itemCategory) throws Exception {
        if (itemName.equals(null) || itemDescription.equals(null) || itemCategory.equals(null)){
            throw new MissingFieldsException();
        }
        // valida se o nome está disponível para uso
        if(validateNameIsAvailable(itemName)){
            // Seta todos os atributos do item e cria o item
            UUID theId = uuidService.generateUUID();

            Item theItem = new Item();

            theItem.setItemId(theId);
            theItem.setItemName(itemName);
            theItem.setItemCategory(itemCategory);
            theItem.setItemDescription(itemDescription);

            return itemsRepository.save(theItem);
        }else {
            throw new ItemAlreadyExistException();
        }
    }
    public Item updateItemById(UUID itemId, String itemName, String itemDescription,
                               String itemImage, String itemBrand, Category itemCategory) throws Exception {
        if (itemName.equals(null) || itemDescription.equals(null) || itemCategory.equals(null)){
            throw new MissingFieldsException();
        }
        // instancia o item com que será atualizado
        Item theUpdatedItem = findItemById(itemId);

        //Valida se o nome foi atualizado e caso ele foi atualizado se esse novo nome está disponível
        if(validateNameIsAvailable(itemName) || theUpdatedItem.getItemName().equals(itemName)) {

            theUpdatedItem.setItemName(itemName);
            theUpdatedItem.setItemDescription(itemDescription);
            theUpdatedItem.setItemImage(itemImage);
            theUpdatedItem.setItemBrand(itemBrand);

            itemsRepository.save(theUpdatedItem);

            return theUpdatedItem;
        }else{
            throw new ItemAlreadyExistException();
        }
    }
    public Item updateItemByName(String itemName, String itemDescription, Category itemCategory,
                               String itemImage, String itemBrand) throws Exception {
        // instancia o item com que será atualizado
        Item theUpdatedItem = findItemByName(itemName);

        //Valida se o nome foi atualizado e caso ele foi atualizado se esse novo nome está disponível
        if(validateNameIsAvailable(itemName) || theUpdatedItem.getItemName().equals(itemName)) {

            theUpdatedItem.setItemName(itemName);
            theUpdatedItem.setItemDescription(itemDescription);
            theUpdatedItem.setItemCategory(itemCategory);
            theUpdatedItem.setItemImage(itemImage);
            theUpdatedItem.setItemBrand(itemBrand);

            itemsRepository.save(theUpdatedItem);

            return theUpdatedItem;
        }else{
            throw new ItemAlreadyExistException();
        }
    }
    public Item findItemById(UUID theItemId) {

        TypedQuery<Item> theQuery = entityManager.createQuery(
                "FROM Item WHERE itemId=:theId", Item.class);

        theQuery.setParameter("theId",theItemId);

        //Procura item por ID
        try {
            return theQuery.getSingleResult();
        }catch (Exception e){
            throw new ItemNotFoundException();
        }
    }

    public Item findItemByName(String theItemName){
        TypedQuery<Item> theQuery = entityManager.createQuery(
                "FROM Item WHERE itemName=:theName", Item.class);

        theQuery.setParameter("theName",theItemName);
        // Procura item por nome
        try {
            return theQuery.getSingleResult();
        }catch (Exception e){
            throw new ItemNotFoundException();
        }

    }

    Boolean validateNameIsAvailable(String theName){
        //Valida se o nome está disponível, false = já está em uso, true = está disponível.
        try{
            findItemByName(theName);
            return false;
        }catch (Exception e){
            return true;
        }
    }

    public List<Item> listAllItems() {
        TypedQuery<Item> theQuery = entityManager.createQuery(
                "FROM Item", Item.class);
        // Lita todos os itens
        try {
            return theQuery.getResultList();
        }catch (Exception e){
            throw new ItemNotFoundException();
        }

    }

    public void deleteItemById(UUID theItemId) throws Exception {
        // Instancia o item
        Item theItem = findItemById(theItemId);
        // Deleta o item
        itemsRepository.delete(theItem);
    }

    public void deleteItemByName(String theItemName) throws Exception {
        // Instancia o item
        Item theItem = findItemByName(theItemName);
        // Deleta o item
        itemsRepository.delete(theItem);
    }
}

