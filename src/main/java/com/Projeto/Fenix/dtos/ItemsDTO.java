package com.Projeto.Fenix.dtos;

import com.Projeto.Fenix.domain.items.Category;

import java.util.UUID;

public record ItemsDTO(UUID itemId, String itemName, String itemDescription, Category itemCategory, String itemImage, String itemBrand) {
}
