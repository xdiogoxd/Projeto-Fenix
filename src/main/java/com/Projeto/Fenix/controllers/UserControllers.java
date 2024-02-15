package com.Projeto.Fenix.controllers;

import com.Projeto.Fenix.domain.users.Users;
import com.Projeto.Fenix.dtos.UserDTO;
import com.Projeto.Fenix.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserControllers {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Users> createNewUser(@RequestBody UserDTO userDTO) throws Exception {
        Users newUser = userService.createNewUser(userDTO.username(), userDTO.password(), userDTO.email());
        return new ResponseEntity<Users>(newUser, HttpStatus.OK);

    }

}
