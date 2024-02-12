package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.shoppingList.ShoppingListDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingListDetailsRepository extends JpaRepository <ShoppingListDetails, String> {

//    List<ShoppingListDetails>  listAllItemsByList(String listId);
}
