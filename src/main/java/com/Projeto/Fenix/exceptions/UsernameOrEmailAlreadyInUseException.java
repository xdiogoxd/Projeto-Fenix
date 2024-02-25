package com.Projeto.Fenix.exceptions;

public class UsernameOrEmailAlreadyInUseException extends RuntimeException{

    public UsernameOrEmailAlreadyInUseException(){
        super("Usuário ou email já está em uso");
    }
}
