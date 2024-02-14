package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findUserByUserId(UUID theId);

//    Users findUserByUserEmail(String theEmail);

    UserDetails findUserByUserUsername(String theUsername);

}
