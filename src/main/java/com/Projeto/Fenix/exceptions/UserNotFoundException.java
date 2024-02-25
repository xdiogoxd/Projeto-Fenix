package com.Projeto.Fenix.exceptions;


public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message){
        super(message);
    }

    public UserNotFoundException(){
        super("Usuário não encontrado");
    }

 }
