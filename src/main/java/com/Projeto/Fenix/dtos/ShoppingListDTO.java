package com.Projeto.Fenix.dtos;

import java.util.Date;
import java.util.UUID;

public record ShoppingListDTO(String listName, UUID listId, String listDescription,
                              String listImage, Date creationDate, Date goalDate) {
}
