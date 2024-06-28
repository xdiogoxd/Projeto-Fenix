package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.items.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemsRepository extends JpaRepository<Item, UUID>{

    Item findItemByItemId(UUID theId);

    Item findItemByItemName(String theName);

    List<Item> listAllItems();
}
