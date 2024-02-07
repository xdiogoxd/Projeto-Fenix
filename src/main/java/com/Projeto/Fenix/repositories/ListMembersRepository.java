package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.shoppingList.ListMembers;
import com.Projeto.Fenix.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListMembersRepository extends JpaRepository <ListMembers, String> {

    void addNewMember(String listId, String requesterId, String memberId);

    void updateMemberAccess(String listId, String requesterId, String memberId, String newRole);

    void removeMember(String listId, String requesterId, String memberId);
}
