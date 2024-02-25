package com.Projeto.Fenix.exceptions;

public class ItemAlreadyExistException extends RuntimeException{
    public ItemAlreadyExistException(){
        super("Nome indisponível");
    }
}
