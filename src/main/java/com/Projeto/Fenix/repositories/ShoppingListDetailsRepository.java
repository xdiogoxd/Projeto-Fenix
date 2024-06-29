package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.shoppingList.ShoppingListDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShoppingListDetailsRepository extends JpaRepository <ShoppingListDetails, UUID> {

//    List<ShoppingListDetails>  listAllItemsByList(String listId);
}
