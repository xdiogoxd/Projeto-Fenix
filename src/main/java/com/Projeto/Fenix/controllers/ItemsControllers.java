package com.Projeto.Fenix.controllers;

import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.dtos.ItemsDTO;
import com.Projeto.Fenix.services.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/Items")
public class ItemsControllers {

    @Autowired
    ItemsService itemsService;
    @PostMapping
    public ResponseEntity<Item> addNewItem(@RequestBody ItemsDTO itemsDTO) throws Exception {
        Item newItem = itemsService.addNewItem(itemsDTO.requester(), itemsDTO.itemName(), itemsDTO.itemDescription(), itemsDTO.itemCategory());
        return new ResponseEntity<Item>(newItem, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Item> updateItemByName(@RequestBody ItemsDTO itemsDTO) throws Exception{
        Item updatedItem = itemsService.updateItemById(itemsDTO.requester(), itemsDTO.itemId(), itemsDTO.itemName(), itemsDTO.itemDescription(), itemsDTO.itemImage(), itemsDTO.itemBrand());
        return new ResponseEntity<Item>(updatedItem, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Item>> listAllItems() throws Exception{
        List<Item> getAllItems = itemsService.listAllItems();
        return new ResponseEntity<List<Item>>(getAllItems, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Item> listItemById(@RequestBody ItemsDTO itemsDTO) throws Exception{
        Item getItem = itemsService.findItemById(itemsDTO.itemId());
        return new ResponseEntity<Item>(getItem, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<Item> listItemByName(@RequestBody ItemsDTO itemsDTO) throws Exception{
        Item getItem = itemsService.findItemByName(itemsDTO.itemName());
        return new ResponseEntity<Item>(getItem, HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public ResponseEntity<String> deleteItemById(@RequestBody ItemsDTO itemsDTO) throws Exception{
        itemsService.deleteItemById(itemsDTO.itemId(), itemsDTO.requester());
        return new ResponseEntity<String>("Item deletado",HttpStatus.OK);
    }

    @DeleteMapping("/name")
    public ResponseEntity<String> deleteItemByName(@RequestBody ItemsDTO itemsDTO) throws Exception {
        itemsService.deleteItemByName(itemsDTO.itemName(), itemsDTO.requester());
        return new ResponseEntity<String>("Item deletado",HttpStatus.OK);
    }
}
