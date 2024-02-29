package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends JpaRepository <ShoppingList, String> {

//    List<ShoppingList> listAllListsByUser(String userId);

    }
