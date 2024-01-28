package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {

    Optional<Users> findUserById(String theId);
}
