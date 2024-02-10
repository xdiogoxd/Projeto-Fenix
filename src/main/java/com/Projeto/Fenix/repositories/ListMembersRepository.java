package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.shoppingList.ListMembers;
import com.Projeto.Fenix.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListMembersRepository extends JpaRepository <ListMembers, String> {

    List<ListMembers> listMembersByList(String listId);

    List<ListMembers> listMembersByMember(String userId);

}
