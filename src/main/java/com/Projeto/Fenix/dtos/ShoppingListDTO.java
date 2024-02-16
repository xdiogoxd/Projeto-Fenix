package com.Projeto.Fenix.dtos;

import com.Projeto.Fenix.domain.users.Users;

import java.util.UUID;

public record ShoppingListDTO(UUID owner, String shoppingListName) {
}
