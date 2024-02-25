package com.Projeto.Fenix.exceptions;

public class CategoryAlreadyExistException extends RuntimeException{
    public CategoryAlreadyExistException(){
        super("Nome indisponível");
    }
}
