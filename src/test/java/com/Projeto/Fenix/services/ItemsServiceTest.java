package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Category;
import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.exceptions.ItemAlreadyExistException;
import com.Projeto.Fenix.exceptions.ItemNotFoundException;
import com.Projeto.Fenix.exceptions.MissingFieldsException;
import com.Projeto.Fenix.repositories.ItemsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class ItemsServiceTest {

    @Mock
    UuidService uuidService;

    @Mock
    ItemsRepository itemsRepository;

    @InjectMocks
    ItemsService itemsService;

    Item testItem;

    Category testCategory;

    @BeforeEach
    public void setup(){
        testCategory.setCategoryName("Cozinha");
        testCategory.setCategoryId(UUID.randomUUID());
        testCategory.setCategoryDescription("Item de cozinha");
        testCategory.setCategoryIcon("icon");

        testItem.setItemName("Test Item");
        testItem.setItemDescription("Description");
        testItem.setItemCategory(testCategory);
    }

    @DisplayName("Adiciona novo item com sucesso")
    @Test
    void addNewItemSuccess() throws Exception {
        given(itemsRepository.findItemByItemName(anyString())).willThrow(Exception.class);
        given(itemsRepository.save(any())).willReturn(testItem);

        Item theItem = itemsService.addNewItem(testItem.getItemName(),testItem.getItemDescription(),testItem.getItemCategory());

        assertEquals(theItem.getItemName(), testItem.getItemName());
        verify(itemsRepository, times(1)).save(any());
    }

    @DisplayName("Tenta adicionar item com nome duplicado")
    @Test
    void addNewItemDuplicatedName() {
        given(itemsRepository.findItemByItemName(any())).willReturn(testItem);

        assertThrows(ItemAlreadyExistException.class, () -> {
            itemsService.addNewItem(testItem.getItemName(),testItem.getItemDescription(),testItem.getItemCategory());
        });

        verify(itemsRepository, times(0)).save(any());
    }

    @DisplayName("Tenta adicionar item com campos nulos")
    @Test
    void addNewItemWithNullFields() {
        assertThrows(MissingFieldsException.class, () -> {
            itemsService.addNewItem(testItem.getItemName(),null,testItem.getItemCategory());
        });

        verify(itemsRepository, times(0)).save(any());
    }

    @DisplayName("Atualiza item por Id com sucesso")
    @Test
    void updateItemByIdSuccess() throws Exception {
        testItem.setItemId(UUID.randomUUID());

        given(itemsRepository.findItemByItemId(any())).willReturn(testItem);
        given(itemsRepository.findItemByItemName(any())).willReturn(testItem);

        Item updatedItem = itemsService.updateItemById(testItem.getItemId(), testItem.getItemName(), testItem.getItemDescription()
                ,testItem.getItemImage(), testItem.getItemBrand(), testItem.getItemCategory());

        verify(itemsRepository,times(1)).save(any());
        assertEquals(updatedItem.getItemName(), testItem.getItemName());
    }

    @DisplayName("Tenta atualizar item por ID com nome duplicado")
    @Test
    void updateItemByIdWithDuplicatedName() {
        Item testItem2 = new Item();
        testItem2.setItemId(UUID.randomUUID());
        testItem2.setItemName("test2");
        testItem2.setItemDescription("test2");
        testItem2.setItemCategory(testCategory);

        testItem.setItemId(UUID.randomUUID());
        testItem.setItemName("test2");

        given(itemsRepository.findItemByItemId(testItem.getItemId())).willReturn(testItem);
        given(itemsRepository.findItemByItemName(testItem.getItemName())).willReturn(testItem2);

        assertThrows(ItemAlreadyExistException.class, () -> {
            itemsService.updateItemById(testItem.getItemId(), testItem.getItemName(), testItem.getItemDescription()
                    ,testItem.getItemImage(), testItem.getItemBrand(), testItem.getItemCategory());
        });

        verify(itemsRepository, times(0)).save(any());

    }

    @DisplayName("Tenta atualizar item por ID com campos nulos")
    @Test
    void updateItemByIdWithNullFields() {

        assertThrows(MissingFieldsException.class, () -> {
            itemsService.updateItemById(testItem.getItemId(), null, testItem.getItemDescription()
                    ,testItem.getItemImage(), testItem.getItemBrand(), testItem.getItemCategory());
        });

        verify(itemsRepository, times(0)).save(any());
        verify(itemsRepository, times(0)).findItemByItemName(any());
        verify(itemsRepository, times(0)).findItemByItemId(any());
    }

    @DisplayName("Atualiza item por nome com sucesso")
    @Test
    void updateItemByNameSuccess() throws Exception {
        testItem.setItemId(UUID.randomUUID());

        given(itemsRepository.findItemByItemId(any())).willReturn(testItem);
        given(itemsRepository.findItemByItemName(any())).willReturn(testItem);

        Item updatedItem = itemsService.updateItemByName(testItem.getItemName(), testItem.getItemDescription()
                ,testItem.getItemImage(), testItem.getItemBrand(), testItem.getItemCategory());

        verify(itemsRepository,times(1)).save(any());
        assertEquals(updatedItem.getItemName(), testItem.getItemName());
    }

    @DisplayName("Tenta atualizar item por nome com campos nulos")
    @Test
    void updateItemByNameWithNullFields() {
        assertThrows(MissingFieldsException.class, () -> {
            itemsService.updateItemByName(null, testItem.getItemDescription()
                    ,testItem.getItemImage(), testItem.getItemBrand(), testItem.getItemCategory());
        });

        verify(itemsRepository, times(0)).save(any());
        verify(itemsRepository, times(0)).findItemByItemName(any());
        verify(itemsRepository, times(0)).findItemByItemId(any());
    }

    @DisplayName("Procura Item por ID com sucesso")
    @Test
    void findItemByIdSuccess() {
        testItem.setItemId(UUID.randomUUID());
        given(itemsRepository.findItemByItemId(any())).willReturn(testItem);

        Item theItem = itemsService.findItemById(testItem.getItemId());

        verify(itemsRepository, times(1)).findItemByItemId(any());
        assertEquals(theItem.getItemName(), testItem.getItemName());
    }

    @DisplayName("Tenta procurar Item por ID sem resultados")
    @Test
    void findItemByIdWithNoResults() {
        testItem.setItemId(UUID.randomUUID());
        given(itemsRepository.findItemByItemId(any())).willThrow(Exception.class);

        assertThrows(ItemNotFoundException.class, () -> {
            itemsService.findItemById(testItem.getItemId());
        });

        verify(itemsRepository, times(1)).findItemByItemId(any());
    }

    @DisplayName("Tenta procurar Item por ID com input null")
    @Test
    void findItemByIdWithNullFields() {
        assertThrows(MissingFieldsException.class, () -> {
            itemsService.findItemById(null);
        });
    }

    @DisplayName("Procura Item por nome com sucesso")
    @Test
    void findItemByNameSuccess() {
        testItem.setItemId(UUID.randomUUID());
        given(itemsRepository.findItemByItemName(any())).willReturn(testItem);

        Item theItem = itemsService.findItemByName(testItem.getItemName());

        verify(itemsRepository, times(1)).findItemByItemId(any());
        assertEquals(theItem.getItemName(), testItem.getItemName());
    }

    @DisplayName("Tenta procurar Item por nome sem resultados")
    @Test
    void findItemByNameWithNoResults() {
        given(itemsRepository.findItemByItemName(any())).willThrow(Exception.class);

        assertThrows(ItemNotFoundException.class, () -> {
            itemsService.findItemByName(testItem.getItemName());
        });

        verify(itemsRepository, times(1)).findItemByItemId(any());
    }

    @DisplayName("Tenta procurar Item por nome com input null")
    @Test
    void findItemByNameWithNullFields() {
        assertThrows(MissingFieldsException.class, () -> {
            itemsService.findItemById(null);
        });
    }

    @DisplayName("Lista todos itens")
    @Test
    void listAllItemsSuccess() {
        List<Item> theList = new ArrayList<>();
        theList.add(testItem);
        given(itemsRepository.listAllItems()).willReturn(theList);

        List<Item> theNewList = itemsService.listAllItems();

        verify(itemsRepository, times(1)).listAllItems();
        assertEquals(theNewList.size(),1);
    }

    @DisplayName("Lista todos itens sem resultados")
    @Test
    void listAllItemsWithNoResults() {
        given(itemsRepository.listAllItems()).willThrow(Exception.class);

        assertThrows(ItemNotFoundException.class, () -> {
            itemsService.listAllItems();
        });

        verify(itemsRepository, times(1)).listAllItems();
    }

    @DisplayName("Deleta item por ID com sucesso")
    @Test
    void deleteItemByIdSuccess() throws Exception {
        testItem.setItemId(UUID.randomUUID());
        given(itemsRepository.findItemByItemId(testItem.getItemId())).willReturn(testItem);

        itemsService.deleteItemById(testItem.getItemId());

        verify(itemsRepository, times(1)).findItemByItemId(any());
        verify(itemsRepository, times(1)).delete(any());
    }

    @DisplayName("Tenta deletar item por Id com item não localizado")
    @Test
    void deleteItemByIdWithNoResult() throws Exception {
        testItem.setItemId(UUID.randomUUID());
        given(itemsRepository.findItemByItemId(testItem.getItemId())).willThrow(Exception.class);

        assertThrows(ItemNotFoundException.class, () -> {
            itemsService.deleteItemById(testItem.getItemId());
        });

        verify(itemsRepository, times(1)).findItemByItemId(any());
        verify(itemsRepository, times(0)).delete(any());
    }

    @DisplayName("Tenta deletar item por Id com null input")
    @Test
    void deleteItemByIdWithNullFields() {
        assertThrows(MissingFieldsException.class, () -> {
            itemsService.deleteItemById(null);
        });

        verify(itemsRepository, times(0)).findItemByItemId(any());
    }

    @DisplayName("Deleta item por nome com sucesso")
    @Test
    void deleteItemByName() throws Exception {
        given(itemsRepository.findItemByItemName(testItem.getItemName())).willReturn(testItem);

        itemsService.deleteItemByName(testItem.getItemName());

        verify(itemsRepository, times(1)).findItemByItemName(any());
        verify(itemsRepository, times(1)).delete(any());
    }

    @DisplayName("Tenta deletar item por nome com item não localizado")
    @Test
    void deleteItemByNameWithNoResult() {
        given(itemsRepository.findItemByItemName(testItem.getItemName())).willThrow(Exception.class);

        assertThrows(ItemNotFoundException.class, () -> {
            itemsService.deleteItemByName(testItem.getItemName());
        });

        verify(itemsRepository, times(1)).findItemByItemName(any());
        verify(itemsRepository, times(0)).delete(any());
    }

    @DisplayName("Tenta deletar item por nome com null input")
    @Test
    void deleteItemByNameWithNullFields() {
        assertThrows(MissingFieldsException.class, () -> {
            itemsService.deleteItemByName(null);
        });

        verify(itemsRepository, times(0)).findItemByItemId(any());
    }
}