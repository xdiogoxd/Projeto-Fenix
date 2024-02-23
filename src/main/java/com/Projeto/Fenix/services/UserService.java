package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.users.Users;
import com.Projeto.Fenix.repositories.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.catalina.User;
import org.hibernate.id.uuid.UuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UsersRepository repository;

    @Autowired
    private UuidService uuidService;

    @Autowired
    EntityManager entityManager;

    public Users createNewUser(String theUsername, String thePassword, String theEmail) throws Exception{
        Users theUser = new Users();
        UUID theId = uuidService.generateUUID();
        if((validateEmailUnique(theEmail) && validateUsernameUnique(theUsername))){
            throw new Exception("Username ou Email já está em uso");
        }
        theUser.setUserId(theId);
        theUser.setUserUsername(theUsername);
        theUser.setUserPassword(thePassword);
        theUser.setUserEmail(theEmail);
        theUser.setUserRole("User");
        theUser.setUserImage("");
        theUser.setUserDisplayName("User");

        System.out.println(theUser.getUserEmail());
        if (theUser != null) {
            repository.save(theUser);
        }
        return theUser;
    }


    boolean validateEmailUnique(String theEmail) throws Exception {
        System.out.println("checking email " + theEmail);
        if (findUserByUserEmail(theEmail) != null){
            System.out.println("email ok");
            return true;
        }else {
            System.out.println("email nok");
            return false;
        }
    }

    boolean validateUsernameUnique(String theUsername) throws Exception {
        System.out.println("checking username");
        if (findUserByUserUsername(theUsername) == null){
            System.out.println("username ok");
            return true;
        }else {
            System.out.println("username nok");
            return false;
        }
    }

    Boolean validateUserAuthorization(UUID requesterId) throws Exception {
        Users requester = findUserByUserId(requesterId);

        System.out.println("id: "+ requester.getUserId()+" role: " +requester.getUserRole());

        if(requester.getUserRole().equals("admin")){
            return true;
        }else {
            return false;
        }
    }


    Users findUserByUserEmail(String theEmail){
        TypedQuery<Users> theQuery = entityManager.createQuery(
                "FROM Users WHERE userEmail=:theData", Users.class);

        theQuery.setParameter("theData", theEmail);
        try {
            return theQuery.getSingleResult();
        }catch (Exception e){
            return null;
        }
//        if(test != null){
//            System.out.println("test");
//            return test;
//
//        }
//        System.out.println("test1email");
//
//        return null;
    }

    Users findUserByUserUsername(String theUsername)throws Exception{
        TypedQuery<Users> theQuery = entityManager.createQuery(
                "FROM Users WHERE userUsername=:theData", Users.class);

        theQuery.setParameter("theData", theUsername);
        if(theQuery.getSingleResult().getUserUsername() == null){
            throw new Exception("Usuário não encontrado");
        }

        return theQuery.getSingleResult();
    }

    Users findUserByUserId(UUID theId)throws Exception{
        TypedQuery<Users> theQuery = entityManager.createQuery(
                "FROM Users WHERE userId=:theData", Users.class);

        System.out.println(theId);

        theQuery.setParameter("theData", theId);

        Users theUser = theQuery.getSingleResult();
        if(theUser.getUserId() == null){
            throw new Exception("Usuário não encontrado");
        }
        return theUser;
        }
}
