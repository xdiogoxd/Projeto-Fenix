package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.user.UserRole;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.exceptions.MissingFieldsException;
import com.Projeto.Fenix.exceptions.UserNotFoundException;
import com.Projeto.Fenix.exceptions.UserUnauthorizedException;
import com.Projeto.Fenix.exceptions.UsernameOrEmailAlreadyInUseException;
import com.Projeto.Fenix.infra.security.TokenService;
import com.Projeto.Fenix.repositories.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
        if (theUsername != null && thePassword != null && theEmail != null){
            try {
                userRepository.findUserByUserEmail(theEmail);
            }catch (Exception e){
                try {
                    userRepository.findUserByUserUsername(theUsername);
                }catch (Exception ex){
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
                }
            }
            throw new UsernameOrEmailAlreadyInUseException();
        }
        throw new MissingFieldsException();
    }

    public void validateUserAuthorization(User requester) throws Exception {
        if(!requester.getUserRole().equals(UserRole.ADMIN)){
            throw new UserUnauthorizedException();
        }
    }


    User findUserByUserEmail(String theEmail){
        if (theEmail != null){
            try {
                return userRepository.findUserByUserEmail(theEmail);
            }catch (Exception e){
                throw new UserNotFoundException();
            }
        }
        throw new MissingFieldsException();
    }

    User findUserByUserUsername(String theUsername)throws Exception{
        if (theUsername != null){
            try {
                return userRepository.findUserByUserUsername(theUsername);
            }catch (Exception e){
                throw new UserNotFoundException();
            }
        }
        throw new MissingFieldsException();
    }

    public User findUserByUserId(UUID theId)throws Exception {
        if (theId != null){
            try {
                return userRepository.findUserByUserId(theId);
            }catch (Exception e){
                throw new UserNotFoundException();
            }
        }
        throw new MissingFieldsException();
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
        if (userId != null){
            try {
                User theUser = userRepository.findUserByUserId(userId);
                User theUserPublicInfo = new User();
                theUserPublicInfo.setUserDisplayName(theUser.getUserDisplayName());
                theUserPublicInfo.setUserId(theUser.getUserId());
                return theUserPublicInfo;
            } catch (Exception e){
                throw new UserNotFoundException();
            }

        }
        throw new MissingFieldsException();
    }
}
