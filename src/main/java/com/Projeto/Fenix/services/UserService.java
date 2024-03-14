package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.user.UserRole;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.exceptions.UserNotFoundException;
import com.Projeto.Fenix.exceptions.UserUnauthorizedException;
import com.Projeto.Fenix.exceptions.UsernameOrEmailAlreadyInUseException;
import com.Projeto.Fenix.infra.security.TokenService;
import com.Projeto.Fenix.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UuidService uuidService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    TokenService tokenService;

    public User createNewUser(String theUsername, String thePassword, String theEmail) throws Exception{
        if((validateEmailUnique(theEmail) && validateUsernameUnique(theUsername))){
            User theUser = new User();
            UUID theId = uuidService.generateUUID();
            String encryptedPassword = new BCryptPasswordEncoder().encode(thePassword);

            theUser.setUserId(theId);
            theUser.setUserUsername(theUsername);
            theUser.setUserPassword(encryptedPassword);
            theUser.setUserEmail(theEmail);
            theUser.setUserRole(UserRole.ADMIN);
            theUser.setUserImage("");
            theUser.setUserDisplayName("User");

            System.out.println(theUser.getUserEmail());
            return userRepository.save(theUser);
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

    public void validateUserAuthorization(User requester) throws Exception {
        System.out.println("id: "+ requester.getUserId()+" role: " +requester.getUserRole());
        if(!requester.getUserRole().equals(UserRole.ADMIN)){
            throw new UserUnauthorizedException();
        }
    }


    User findUserByUserEmail(String theEmail){
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

    public User findUserByUserId(UUID theId)throws Exception {
        TypedQuery<User> theQuery = entityManager.createQuery(
                "FROM User WHERE userId=:theData", User.class);

        theQuery.setParameter("theData", theId);
        try {
            return theQuery.getSingleResult();
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }

    public User findUserByToken(HttpServletRequest request){
        var token = this.recoverToken(request);
        var username = tokenService.validateToken(token);
        User theUser = userRepository.findUserByUserUsername(username);
        return theUser;

    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null){
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }

    public User findUserByIdPublicInfo(UUID userId){
        TypedQuery<User> theQuery = entityManager.createQuery(
                "FROM User where userId=:theData",User.class);
        theQuery.setParameter("theData",userId);
        User theUserPublicInfo = new User();

        try {
            User theUser = theQuery.getSingleResult();
            theUserPublicInfo.setUserDisplayName(theUser.getUserDisplayName());
            theUserPublicInfo.setUserId(theUser.getUserId());
            return theUserPublicInfo;
        }catch (Exception e){
            throw new UserNotFoundException();
        }
    }
}
