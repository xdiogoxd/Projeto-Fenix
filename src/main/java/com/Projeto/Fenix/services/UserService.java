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

    public Optional<Users> createNewUser(String theUsername, String thePassword, String theEmail){
        Users theUser = null;
        String theId = uuidService.generateUUID().toString();
        theUser.setUserId(theId);
        theUser.setUsername(theUsername);
        theUser.setPassword(thePassword);
        theUser.setUserEmail(theEmail);
        theUser.setUserRole("User");

        repository.save(theUser);

        return repository.findUserById(theUser.getUserId());
    }

}
