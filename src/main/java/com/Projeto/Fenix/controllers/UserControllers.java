package com.Projeto.Fenix.controllers;

import com.Projeto.Fenix.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserControllers {
    @Autowired
    UserService userService;



}
