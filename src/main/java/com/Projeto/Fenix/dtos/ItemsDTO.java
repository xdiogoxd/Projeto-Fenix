package com.Projeto.Fenix.dtos;

import java.util.UUID;

public record ItemsDTO(UUID itemId, String itemName, String itemDescription, String itemCategory, String itemImage, String itemBrand) {
}
