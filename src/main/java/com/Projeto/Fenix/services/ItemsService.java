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
        //Valida se nenhum dos campos enviados foram nulos
        if (itemName != null && itemDescription != null && itemCategory != null){
            try{
                //Valida se o nome já está sendo utilizado
                findItemByName(itemName);
            }catch (Exception e){
                // Seta todos os atributos do item e cria o item
                UUID theId = uuidService.generateUUID();
                Item theItem = new Item();
                theItem.setItemId(theId);
                theItem.setItemName(itemName);
                theItem.setItemCategory(itemCategory);
                theItem.setItemDescription(itemDescription);

                //Salva item no banco
                return itemsRepository.save(theItem);
            }
            //Caso o nome do item já esteja sendo utilizado, retorna exceção
            throw new ItemAlreadyExistException();
        }
        throw new MissingFieldsException();
    }
    public Item updateItemById(UUID itemId, String itemName, String itemDescription, String itemImage,
                               String itemBrand, Category itemCategory) throws Exception {
        //Valida se nenhum dos campos é nulo
        if (itemName != null || itemDescription != null || itemCategory != null){
            Item updatedItem = new Item();
            //Localiza o item
            try{
                updatedItem = findItemById(itemId);
                updatedItem.setItemName(itemName);
                updatedItem.setItemDescription(itemDescription);
                updatedItem.setItemImage(itemImage);
                updatedItem.setItemBrand(itemBrand);
                updatedItem.setItemCategory(itemCategory);
            } catch (Exception e){
                //Caso o item não seja localiza lança exceção
                throw new ItemNotFoundException();
            }
            try{
                Item theItem = findItemByName(itemName);
                //Valida se o nome do item está disponível
                if (theItem.getItemId() == updatedItem.getItemId()){
                    return itemsRepository.save(updatedItem);
                }
            } catch (Exception e){
                return itemsRepository.save(updatedItem);
            }
            throw new ItemAlreadyExistException();
        }
            throw new MissingFieldsException();
    }
    public Item updateItemByName(String itemName, String itemDescription, Category itemCategory,
                               String itemImage, String itemBrand) throws Exception {
        // instancia o item com que será atualizado
        Item theUpdatedItem = findItemByName(itemName);

        //Valida se o nome foi atualizado e caso ele foi atualizado se esse novo nome está disponível
        theUpdatedItem.setItemName(itemName);
        theUpdatedItem.setItemDescription(itemDescription);
        theUpdatedItem.setItemCategory(itemCategory);
        theUpdatedItem.setItemImage(itemImage);
        theUpdatedItem.setItemBrand(itemBrand);

        itemsRepository.save(theUpdatedItem);

        return theUpdatedItem;
    }
    public Item findItemById(UUID theItemId) {
        if (theItemId != null){
            Item theItem = itemsRepository.findItemByItemId(theItemId);
            if (theItem != null){
                return theItem;
            }else {
                throw new ItemNotFoundException();
            }
        }
        //Procura item por ID
        throw new MissingFieldsException();
    }

    public Item findItemByName(String theItemName){
        if (theItemName != null){
            Item theItem = itemsRepository.findItemByItemName(theItemName);
            if (theItem != null){
                return theItem;
            }else {
                throw new ItemNotFoundException();
            }
        }
        throw new MissingFieldsException();
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

