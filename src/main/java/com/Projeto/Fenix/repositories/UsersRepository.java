package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.users.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {

    Optional<Users> createNewUser(Users theUser);

    Optional<Users> findUserById(String theId);

    Optional<Users> findUserByEmail(String theEmail);

}
