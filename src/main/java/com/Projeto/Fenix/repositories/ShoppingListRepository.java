package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShoppingListRepository extends JpaRepository <ShoppingList, UUID> {

    ShoppingList findShoppingListByListId();

    }
