package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.shoppingList.ListShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListShareRepository extends JpaRepository<ListShare, String> {

    List<String> listInviteCodesByList(String requesterId, String listId);
}
