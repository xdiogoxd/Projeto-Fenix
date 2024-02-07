package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingListRepository extends JpaRepository <ShoppingList, String> {

    Optional<ShoppingList> createShoppingList(String name);

    Optional<ShoppingList> updateShoppingList(ShoppingList updatedShoppingList);

    void deleteShoppingListById (String theId);

    Optional<ShoppingList> duplicateShoppingList(String listId, String requester);

}
