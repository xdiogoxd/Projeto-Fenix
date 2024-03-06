package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.shoppingList.ListMemberRoles;
import com.Projeto.Fenix.domain.shoppingList.ListMembers;
import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.exceptions.ListMemberNotFound;
import com.Projeto.Fenix.exceptions.ListNotFound;
import com.Projeto.Fenix.exceptions.UserNotAuthorized;
import com.Projeto.Fenix.repositories.ListMembersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class ShoppingListMembersService {

    @Autowired
    ListMembersRepository listMembersRepository;

    @Autowired
    ShoppingListService shoppingListService;

    @Autowired
    UuidService uuidService;

    @Autowired
    EntityManager entityManager;

    void addListMemberOwner(User listOwner, ListMemberRoles role, ShoppingList theShoppingList) {
        ListMembers newListMember = new ListMembers();
        UUID theMemberListId = uuidService.generateUUID();

        newListMember.setListMembersId(theMemberListId);
        newListMember.setListId(theShoppingList);
        newListMember.setMemberId(listOwner);
        newListMember.setListRole(role);

        listMembersRepository.save(newListMember);
    }

    public void addListMember(User listOwner,UUID newMember, ListMemberRoles role, UUID shoppingListId) throws Exception {
        ShoppingList theShoppingList = shoppingListService.findShoppingListById(listOwner, shoppingListId);



        ListMembers newListMember = new ListMembers();
        UUID theMemberListId = uuidService.generateUUID();

        newListMember.setListMembersId(theMemberListId);
        newListMember.setListId(theShoppingList);
        newListMember.setMemberId(listOwner);
        newListMember.setListRole(role);

        listMembersRepository.save(newListMember);
    }

    void validateUserAuthorization(User requester, UUID listId){
        ListMembers theMember = findMemberByList(requester.getUserId(), listId);
        if(!theMember.getListRole().equals(ListMemberRoles.ADMIN)){
            throw new UserNotAuthorized();
        }
    }

    ListMembers findMemberByList(UUID memberId, UUID listId){
        TypedQuery<ListMembers> theQuery = entityManager.createQuery(
                "FROM ListMembers WHERE MemberId=:memberId, ListId=:theList", ListMembers.class);

        theQuery.setParameter("memberId", memberId);
        theQuery.setParameter("theList", listId);

        try {
            return theQuery.getSingleResult();
        }catch (Exception e){
            throw new ListMemberNotFound();
        }
    }

    public void updateListMemberAccess(User theOwner, UUID member, ListMemberRoles role) {
    }

    public void deleteListMemberAccess(User theOwner, UUID member) {
    }

    public List<ListMembers> listAllMembersByList(User requester, UUID shoppingListId) {

    }

    public List<ListMembers> listAllListsByMembers(User requester) {
        TypedQuery<ListMembers> theQuery = entityManager.createQuery(
                "FROM ListMembers where memberId=:theData", ListMembers.class);

        theQuery.setParameter("theData", requester.getUserId());

        try {
            return theQuery.getResultList();
        }catch(Exception exception) {
            throw new ListNotFound();
        }
    }
}
