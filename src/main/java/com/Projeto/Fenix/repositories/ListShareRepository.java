package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.shoppingList.ListShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListShareRepository extends JpaRepository<ListShare, String> {

    String createInviteCode(String requesterId, String listId);

    String deleteInviteCode(String requesterId, String listId);

    List<String> listInviteCodes(String requesterId, String listId);
}
