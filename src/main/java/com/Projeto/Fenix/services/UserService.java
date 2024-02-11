package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.users.Users;
import com.Projeto.Fenix.repositories.UsersRepository;
import org.hibernate.id.uuid.UuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UsersRepository repository;

    @Autowired
    private UuidService uuidService;

    public Optional<Users> findUserById(String theId) throws Exception {
        if((this.repository.findUserById(theId).isEmpty())){
            throw new Exception("Usuário não encontrado");
        }
        return this.repository.findUserById(theId);
    }

    public Optional<Users> findUserByEmail(String theEmail) throws Exception {
        if((this.repository.findUserByEmail(theEmail).isEmpty())){
            throw new Exception("Usuário não encontrado");
        }
        return this.repository.findUserById(theEmail);
    }

    public Users createNewUser(String theUsername, String thePassword, String theEmail) throws Exception{
        Users theUser = null;
        String theId = uuidService.generateUUID().toString();
        if(!(validateEmailUnique(theEmail) && validateUsernameUnique(theUsername))){
            throw new Exception("Username ou Email já está em uso");
        }
        theUser.setUserId(theId);
        theUser.setUsername(theUsername);
        theUser.setPassword(thePassword);
        theUser.setUserEmail(theEmail);
        theUser.setUserRole("User");

        repository.save(theUser);

        return theUser;
    }


    boolean validateEmailUnique(String theEmail){
        if (repository.findUserByEmail(theEmail) == null){
            return true;
        }else {
            return false;
        }
    }

    boolean validateUsernameUnique(String theUsername){
        if (repository.findUserByUsername(theUsername) == null){
            return true;
        }else {
            return false;
        }
    }
}
