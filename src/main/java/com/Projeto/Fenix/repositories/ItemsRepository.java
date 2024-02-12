package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.items.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemsRepository extends JpaRepository<Item, String>{

//    Optional<Item> findItemByItemId(String theId);

//    Optional<Item> findItemByItemName(String theName);

//    List<Item> listAllItems();
}
