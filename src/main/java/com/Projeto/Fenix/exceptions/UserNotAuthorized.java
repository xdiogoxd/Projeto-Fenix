package com.Projeto.Fenix.exceptions;

public class UserNotAuthorized extends RuntimeException{
    public UserNotAuthorized(){
        super("Usuário não autorizado para realizar essa ação");
    }
}
