package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.shoppingList.ShoppingListDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListDetailsRepository extends JpaRepository <ShoppingListDetails, String> {

    void addItem (String requesterId, String listId, String itemId, double quantity);

    void removeItem (String listId, String itemId, double quantity);
}
