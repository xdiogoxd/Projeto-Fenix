package com.Projeto.Fenix.exceptions;

public class UserUnauthorizedException extends RuntimeException{

    public UserUnauthorizedException(){
        super("Usuário não autorizado a efetuar operação");
    }
}
