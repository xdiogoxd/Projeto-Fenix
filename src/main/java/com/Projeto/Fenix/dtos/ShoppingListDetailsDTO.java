package com.Projeto.Fenix.dtos;

import java.util.UUID;

public record ShoppingListDetailsDTO(UUID requesterId, UUID listId, UUID itemId, double quantity) {
}
