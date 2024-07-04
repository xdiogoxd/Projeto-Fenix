package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Category;
import com.Projeto.Fenix.domain.items.Item;
import com.Projeto.Fenix.domain.shoppingList.ListMemberRoles;
import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.exceptions.ItemAlreadyExistException;
import com.Projeto.Fenix.exceptions.ItemNotFoundException;
import com.Projeto.Fenix.exceptions.MissingFieldsException;
import com.Projeto.Fenix.exceptions.ShoppingListNotFound;
import com.Projeto.Fenix.repositories.ItemsRepository;
import com.Projeto.Fenix.repositories.ShoppingListRepository;
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



class ShoppingListServiceTest {

    @Mock
    ShoppingListRepository shoppingListRepository;

    @Mock
    ShoppingListMembersService shoppingListMembersService;

    @Mock
    UuidService uuidService;

    @Mock
    ListMemberRoles listMemberRoles;

    @InjectMocks
    ShoppingListService shoppingListService;

    ShoppingList testShoppingList;

    User testUser;

    @BeforeEach
    public void setup(){
        testShoppingList.setListName("A Name");

        testUser.setUserId(UUID.randomUUID());
    }

    @DisplayName("Cria lista com sucesso")
    @Test
    void createShoppingListSuccess() throws Exception {
        given(shoppingListRepository.save(any())).willReturn(testShoppingList);
        willDoNothing().given(shoppingListMembersService).addListMemberOwner(testUser,
                ListMemberRoles.ADMIN,testShoppingList);

        ShoppingList theShoppingList = shoppingListService.createShoppingList(testUser, testShoppingList.getListName());

        verify(shoppingListRepository,times(1)).save(any());
        assertEquals(theShoppingList.getListName(), testShoppingList.getListName());
    }

    @DisplayName("Tenta criar lista com campos nulos")
    @Test
    void createShoppingListWithNullFields() {

        assertThrows(MissingFieldsException.class, () -> {
            shoppingListService.createShoppingList(testUser, null);
        });

        verify(shoppingListRepository, times(0)).save(any());
    }

    @DisplayName("Procura Lista com sucesso")
    @Test
    void findShoppingListByIdSuccess() throws Exception {
        testShoppingList.setListId(UUID.randomUUID());
        given(shoppingListRepository.findShoppingListByListId(any())).willReturn(testShoppingList);

        ShoppingList theShoppingList = shoppingListService.findShoppingListById(testShoppingList.getListId());

        verify(shoppingListRepository,times(1)).findShoppingListByListId(any());
        assertEquals(theShoppingList.getListId(),testShoppingList.getListId());
    }

    @DisplayName("Procura Lista sem resultados")
    @Test
    void findShoppingListByIdWithNoResults() {
        testShoppingList.setListId(UUID.randomUUID());
        given(shoppingListRepository.findShoppingListByListId(any())).willThrow(Exception.class);

        assertThrows(ShoppingListNotFound.class, () -> {
            shoppingListService.findShoppingListById(testShoppingList.getListId());
        });

        verify(shoppingListRepository,times(1)).findShoppingListByListId(any());
    }

    @DisplayName("Procura Lista entrada nula")
    @Test
    void findShoppingListByIdWithNullFields() {
        assertThrows(MissingFieldsException.class, () -> {
            shoppingListService.findShoppingListById(null);
        });

        verify(shoppingListRepository,times(0)).findShoppingListByListId(any());
    }

    @DisplayName("Atualiza lista por Id com sucesso")
    @Test
    void updateShoppingListByIdSuccess() throws Exception {
        testShoppingList.setListId(UUID.randomUUID());

        given(shoppingListRepository.findShoppingListByListId(any())).willReturn(testShoppingList);
        given(shoppingListRepository.save(any())).willReturn(testShoppingList);

        ShoppingList theUpdatedList = shoppingListService.updateShoppingListById(testShoppingList.getListId(),
                testShoppingList.getListName(), testShoppingList.getListDescription(), testShoppingList.getListImage(),
                testShoppingList.getGoalDate());

        verify(shoppingListRepository, times(1)).findShoppingListByListId(any());
        verify(shoppingListRepository, times(1)).save(any());
        assertEquals(theUpdatedList.getListId(), testShoppingList.getListId());
    }

    @DisplayName("Tenta atualizar lista com ID invalido")
    @Test
    void updateShoppingListByIdWithIdNotFound() {
        testShoppingList.setListId(UUID.randomUUID());

        given(shoppingListRepository.findShoppingListByListId(any())).willThrow(Exception.class);

        assertThrows(ShoppingListNotFound.class, () -> {
            shoppingListService.updateShoppingListById(testShoppingList.getListId(),
                    testShoppingList.getListName(), testShoppingList.getListDescription(), testShoppingList.getListImage(),
                    testShoppingList.getGoalDate());
        });

        verify(shoppingListRepository, times(1)).findShoppingListByListId(any());
        verify(shoppingListRepository, times(0)).save(any());
    }

    @DisplayName("Tenta atualizar lista com campos nulos")
    @Test
    void updateShoppingListByIdWithNullFields() {
        assertThrows(MissingFieldsException.class, () -> {
            shoppingListService.updateShoppingListById(null,
                    testShoppingList.getListName(), testShoppingList.getListDescription(), testShoppingList.getListImage(),
                    testShoppingList.getGoalDate());
        });

        verify(shoppingListRepository, times(0)).findShoppingListByListId(any());
        verify(shoppingListRepository, times(0)).save(any());
    }

    @DisplayName("Deleta lista por Id com sucesso")
    @Test
    void deleteShoppingListByIdSuccess() throws Exception {
        testShoppingList.setListId(UUID.randomUUID());

        given(shoppingListRepository.findShoppingListByListId(any())).willReturn(testShoppingList);
        willDoNothing().given(shoppingListRepository).delete(any());

        shoppingListService.deleteShoppingListById(testShoppingList.getListId());

        verify(shoppingListRepository, times(1)).findShoppingListByListId(any());
        verify(shoppingListRepository, times(1)).delete(any());
    }

    @DisplayName("Tenta deletar lista com Id invalido")
    @Test
    void deleteShoppingListByIdInvalidId() throws Exception {
        testShoppingList.setListId(UUID.randomUUID());

        given(shoppingListRepository.findShoppingListByListId(any())).willThrow(Exception.class);

        assertThrows(ShoppingListNotFound.class, () -> {
            shoppingListService.deleteShoppingListById(testShoppingList.getListId());
        });

        verify(shoppingListRepository, times(1)).findShoppingListByListId(any());
        verify(shoppingListRepository, times(0)).delete(any());

    }

    @DisplayName("Tenta deletar lista entrada nula")
    @Test
    void deleteShoppingListByIdNullFields() {
        assertThrows(MissingFieldsException.class, () -> {
            shoppingListService.deleteShoppingListById(null);
        });

        verify(shoppingListRepository, times(0)).findShoppingListByListId(any());
        verify(shoppingListRepository, times(0)).delete(any());
    }
}