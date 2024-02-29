package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.user.UserRole;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.exceptions.UserNotFoundException;
import com.Projeto.Fenix.exceptions.UserUnauthorizedException;
import com.Projeto.Fenix.exceptions.UsernameOrEmailAlreadyInUseException;
import com.Projeto.Fenix.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UuidService uuidService;

    @Autowired
    EntityManager entityManager;

    public User createNewUser(String theUsername, String thePassword, String theEmail) throws Exception{
        User theUser = new User();
        UUID theId = uuidService.generateUUID();

        if((validateEmailUnique(theEmail) && validateUsernameUnique(theUsername))){
            String encryptedPassword = new BCryptPasswordEncoder().encode(thePassword);

            theUser.setUserId(theId);
            theUser.setUserUsername(theUsername);
            theUser.setUserPassword(encryptedPassword);
            theUser.setUserEmail(theEmail);
            theUser.setUserRole(UserRole.ADMIN);
            theUser.setUserImage("");
            theUser.setUserDisplayName("User");

            System.out.println(theUser.getUserEmail());
            return repository.save(theUser);
        }else {
            throw new UsernameOrEmailAlreadyInUseException();
        }


    }


    boolean validateEmailUnique(String theEmail) throws Exception {

        try{
            findUserByUserEmail(theEmail);
            return false;
        }catch (Exception e){
            return true;
        }
    }

    boolean validateUsernameUnique(String theUsername) throws Exception {

        try{
            findUserByUserUsername(theUsername);
            return false;
        }catch (Exception e){
            return true;
        }
    }

    void validateUserAuthorization(UUID requesterId) throws Exception {
        User requester = findUserByUserId(requesterId);

        System.out.println("id: "+ requester.getUserId()+" role: " +requester.getUserRole());

        if(!requester.getUserRole().equals("admin")){
            throw new UserUnauthorizedException();
        }
    }


    User findUserByUserEmail(String theEmail){
        System.out.println(theEmail);
        TypedQuery<User> theQuery = entityManager.createQuery(
                "FROM User WHERE userEmail=:theData", User.class);

        theQuery.setParameter("theData", theEmail);

        try {
            System.out.println(theQuery.getSingleResult());
            return theQuery.getSingleResult();
        }catch (Exception e){
            throw new UserNotFoundException();
        }
    }

    User findUserByUserUsername(String theUsername)throws Exception{
        TypedQuery<User> theQuery = entityManager.createQuery(
                "FROM User WHERE userUsername=:theData", User.class);

        theQuery.setParameter("theData", theUsername);

        try{
            System.out.println(theUsername+theQuery.getSingleResult());
            return theQuery.getSingleResult();
        }catch (Exception e) {
            System.out.println(e);
            throw new Exception(e);
        }

    }

    UserDetails findUserByUserUsernameSS(String theUsername)throws Exception{
        TypedQuery<User> theQuery = entityManager.createQuery(
                "FROM Users WHERE userUsername=:theData", User.class);

        theQuery.setParameter("theData", theUsername);

        try{
            System.out.println(theQuery.getSingleResult());
            return theQuery.getSingleResult();
        }catch (Exception e) {
            throw new UserNotFoundException();
        }

    }

    User findUserByUserId(UUID theId)throws Exception {
        TypedQuery<User> theQuery = entityManager.createQuery(
                "FROM Users WHERE userId=:theData", User.class);

        theQuery.setParameter("theData", theId);

        try {
            return theQuery.getSingleResult();
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }
}
