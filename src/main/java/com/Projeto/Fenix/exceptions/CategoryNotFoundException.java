package com.Projeto.Fenix.exceptions;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(){
        super("Categoria não encontrada");
    }
}
