package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.shoppingList.ListMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListMembersRepository extends JpaRepository <ListMembers, String> {

//    List<ListMembers> listMembersByList(String listId);

//    List<ListMembers> listMembersByMember(String userId);

}
