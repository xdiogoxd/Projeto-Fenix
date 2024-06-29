package com.Projeto.Fenix.services;

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

    @DisplayName("Adiciona novo item com sucesso")
    @Test
    void addNewItemSuccess() {
    }

    @DisplayName("Tenta adicionar item com nome duplicado")
    @Test
    void addNewItemDuplicatedName() {
    }

    @DisplayName("Tenta adicionar item com campos nulos")
    @Test
    void addNewItemWithNullFields() {
    }

    @DisplayName("Atualiza item por Id com sucesso")
    @Test
    void updateItemByIdSuccess() {
    }

    @DisplayName("Tenta atualizar item por ID com nome duplicado")
    @Test
    void updateItemByIdWithDuplicatedName() {
    }

    @DisplayName("Tenta atualizar item por ID com campos nulos")
    @Test
    void updateItemByIdWithNullFields() {
    }

    @DisplayName("Atualiza item por nome com sucesso")
    @Test
    void updateItemByNameSuccess() {
    }

    @DisplayName("Tenta atualizar item por nome com campos nulos")
    @Test
    void updateItemByNameWithNullFields() {
    }

    @DisplayName("Procura Item por ID com sucesso")
    @Test
    void findItemByIdSuccess() {
    }

    @DisplayName("tenta procurar Item por ID sem resultados")
    @Test
    void findItemByIdWithNoResults() {
    }

    @DisplayName("tenta procurar Item por ID com input null")
    @Test
    void findItemByIdWithNullFields() {
    }

    @DisplayName("Procura Item por nome com sucesso")
    @Test
    void findItemByNameSuccess() {
    }

    @DisplayName("tenta procurar Item por nome sem resultados")
    @Test
    void findItemByNameWithNoResults() {
    }

    @DisplayName("tenta procurar Item por nome com input null")
    @Test
    void findItemByNameWithNullFields() {
    }

    @DisplayName("Lista todos itens")
    @Test
    void listAllItemsSuccess() {
    }

    @DisplayName("Lista todos itens sem resultados")
    @Test
    void listAllItemsWithNoResults() {
    }

    @DisplayName("Deleta item por ID com sucesso")
    @Test
    void deleteItemByIdSuccess() {
    }

    @DisplayName("Tenta deletar item por Id com item não localizado")
    @Test
    void deleteItemByIdWithNoResult() {
    }

    @DisplayName("Tenta deletar item por Id com null input")
    @Test
    void deleteItemByIdWithNullFields() {
    }

    @DisplayName("Deleta item por nome com sucesso")
    @Test
    void deleteItemByName() {
    }

    @DisplayName("Tenta deletar item por nome com item não localizado")
    @Test
    void deleteItemByNameWithNoResult() {
    }

    @DisplayName("Tenta deletar item por nome com null input")
    @Test
    void deleteItemByNameWithNullFields() {
    }
}