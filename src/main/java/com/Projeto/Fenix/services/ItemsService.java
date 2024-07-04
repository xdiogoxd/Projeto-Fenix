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
    ItemsRepository itemsRepository;

    public Item addNewItem(String itemName, String itemDescription, Category itemCategory) throws Exception {
        //Valida se nenhum dos campos enviados foram nulos
        if (itemName != null && itemDescription != null && itemCategory != null){
            try{
                //Valida se o nome já está sendo utilizado
                itemsRepository.findItemByItemName(itemName);
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
                updatedItem = itemsRepository.findItemByItemId(itemId);
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
                Item theItem = itemsRepository.findItemByItemName(itemName);
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
    public Item updateItemByName(String itemName, String itemDescription,
                               String itemImage, String itemBrand, Category itemCategory) throws Exception {
        //Valida se nenhum dos campos é nulo
        if (itemName != null || itemDescription != null || itemCategory != null){
            try{
                //Procura o item e caso encontre ele, atualiza os campos e salva
                Item updatedItem = findItemByName(itemName);
                updatedItem.setItemDescription(itemDescription);
                updatedItem.setItemImage(itemImage);
                updatedItem.setItemBrand(itemBrand);
                updatedItem.setItemCategory(itemCategory);
                return itemsRepository.save(updatedItem);
            } catch (Exception e) {
                throw new ItemNotFoundException();
            }
        }
        throw new MissingFieldsException();
    }
    public Item findItemById(UUID theItemId) {
        //Valida se o input não foi nulo
        if (theItemId != null){
            try {
                Item theItem = itemsRepository.findItemByItemId(theItemId);
                return theItem;
            }catch (Exception e){
                throw new ItemNotFoundException();
            }
        }
        throw new MissingFieldsException();
    }

    public Item findItemByName(String theItemName){
        if (theItemName != null){
            try {
                Item theItem = itemsRepository.findItemByItemName(theItemName);
                return theItem;
            }catch (Exception e){
                throw new ItemNotFoundException();
            }
        }
        throw new MissingFieldsException();
    }

    public List<Item> listAllItems() {
        try {
            return itemsRepository.listAllItems();
        }catch (Exception e){
            throw new ItemNotFoundException();
        }
    }

    public void deleteItemById(UUID theItemId) throws Exception {
        if (theItemId != null){
            try {
                Item theItem = findItemById(theItemId);
                itemsRepository.delete(theItem);
            }catch (Exception e){
                throw new ItemNotFoundException();
            }
        }else {
            throw new MissingFieldsException();
        }

    }

    public void deleteItemByName(String theItemName) throws Exception {
        if (theItemName != null){
            try {
                Item theItem = findItemByName(theItemName);
                itemsRepository.delete(theItem);
            }catch (Exception e){
                throw new ItemNotFoundException();
            }
        }else {
            throw new MissingFieldsException();
        }
    }
}

