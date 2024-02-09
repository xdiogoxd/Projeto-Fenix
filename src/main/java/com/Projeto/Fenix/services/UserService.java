package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.users.Users;
import com.Projeto.Fenix.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UsersRepository repository;

    public Optional<Users> findUserById(String theId) throws Exception {
        if((this.repository.findUserById(theId).isEmpty())){
            throw new Exception("Usuário não encontrado");
        }
        return this.repository.findUserById(theId);
    }

}
