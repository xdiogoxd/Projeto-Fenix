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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ShoppingListMembersService {

    @Autowired
    ListMembersRepository listMembersRepository;

    @Autowired
    ShoppingListService shoppingListService;

    @Autowired
    UserService userService;

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

    public void addListMember(User requester,UUID newMember, ListMemberRoles role, UUID shoppingListId) throws Exception {
        //Valida se a solicitação é de criação de um admin ou coadmin, caso seja, apenas um admin pode fazer essa ação
        if (role == ListMemberRoles.ADMIN || role == ListMemberRoles.CO_ADMIN){
            validateUserAuthorization(requester, shoppingListId, ListMemberRoles.ADMIN, ListMemberRoles.ADMIN);
        }
        //Valida autorização do usuário
        validateUserAuthorization(requester, shoppingListId, ListMemberRoles.ADMIN, ListMemberRoles.CO_ADMIN);


        //Carrega shopping list e e novo membro e cria o novo membro para a lista
        ShoppingList theShoppingList = shoppingListService.findShoppingListById(requester, shoppingListId);

        User theNewMember = userService.findUserByUserId(newMember);

        ListMembers newListMember = new ListMembers();
        UUID theMemberListId = uuidService.generateUUID();

        newListMember.setListMembersId(theMemberListId);
        newListMember.setListId(theShoppingList);
        newListMember.setMemberId(theNewMember);
        newListMember.setListRole(role);

        listMembersRepository.save(newListMember);
    }

    void validateUserAuthorization(User requester, UUID listId, ListMemberRoles theMainRole, ListMemberRoles theSecondaryRole){
        // valida autorização de acordo com os parametros enviados
        ListMembers theMember = findMemberByList(requester.getUserId(), listId);
        if((!theMember.getListRole().equals(theMainRole))||(!theMember.getListRole().equals(theSecondaryRole))){
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

    public void updateListMemberAccess(User requester, UUID member, UUID shoppingListId, ListMemberRoles role) {
        validateUserAuthorization(requester, shoppingListId, ListMemberRoles.ADMIN, ListMemberRoles.CO_ADMIN);

        ListMembers theAccess = findMemberByList(member, shoppingListId);

        theAccess.setListRole(role);

        entityManager.merge(role);
    }

    public void deleteListMemberAccess(User requester, UUID memberId, UUID listId) {
        validateUserAuthorization(requester, listId, ListMemberRoles.ADMIN, ListMemberRoles.CO_ADMIN);

        ListMembers theAccess = findMemberByList(memberId, listId);

        entityManager.remove(theAccess);
    }

    public List<User> listAllMembersByList(User requester, UUID listId) {
        validateUserAuthorization(requester, listId, ListMemberRoles.ADMIN, ListMemberRoles.CO_ADMIN);

        TypedQuery<ListMembers> theQuery = entityManager.createQuery(
                "FROM ListMembers where listId =:theData", ListMembers.class);

        theQuery.setParameter("theData", listId);

        List<ListMembers> theMembers = theQuery.getResultList();
        List<User> allUsers = new ArrayList<>();

        if (theMembers.size()==0){
            throw new ListMemberNotFound();
        }

        for (int i = 0;theMembers.size() < i; i++){
            allUsers.add(userService.findUserByIdPublicInfo(theMembers.get(i).getMemberId().getUserId()));
        }

        return allUsers;

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
