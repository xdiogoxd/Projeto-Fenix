package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByUserId(UUID theId);

    User findUserByUserEmail(String theEmail);

    User findUserByUserUsername(String theUsername);

}
