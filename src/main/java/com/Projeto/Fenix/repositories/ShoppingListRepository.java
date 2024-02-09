package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingListRepository extends JpaRepository <ShoppingList, String> {

    ShoppingList createShoppingList(Users owner, String name);

    Optional<ShoppingList> updateShoppingList(ShoppingList updatedShoppingList);

    void deleteShoppingListById (String theId);

    }
