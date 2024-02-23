package com.Projeto.Fenix.dtos;

import java.util.UUID;

public record CategoriesDTO(UUID categoryId, String categoryName, String categoryDescription, String categoryIcon, UUID requester) {
}
