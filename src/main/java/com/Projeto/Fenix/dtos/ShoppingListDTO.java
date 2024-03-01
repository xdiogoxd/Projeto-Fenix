package com.Projeto.Fenix.dtos;

import java.util.Date;
import java.util.UUID;

public record ShoppingListDTO(UUID owner, String shoppingListName, String shoppingListId, String shoppingDescription,
                              String shoppingListImage, Date shoppingListCreationDate, Date shoppingListGoalDate) {
}
