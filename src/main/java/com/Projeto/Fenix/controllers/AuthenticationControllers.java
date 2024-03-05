package com.Projeto.Fenix.controllers;

import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.dtos.AuthenticationDTO;
import com.Projeto.Fenix.dtos.LoginResponseDTO;
import com.Projeto.Fenix.dtos.UserDTO;
import com.Projeto.Fenix.infra.security.TokenService;
import com.Projeto.Fenix.repositories.UserRepository;
import com.Projeto.Fenix.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthenticationControllers {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO authenticationDTO) throws Exception {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }


    @PostMapping("/register")
    public ResponseEntity<User> createNewUser(@RequestBody UserDTO userDTO) throws Exception {
        User newUser = userService.createNewUser(userDTO.username(), userDTO.password(),userDTO.email());

        return new ResponseEntity<User>(newUser, HttpStatus.OK);

    }


}
