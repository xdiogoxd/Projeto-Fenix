package com.Projeto.Fenix.exceptions;

public class ListNotFound extends RuntimeException{
    public ListNotFound(){
        super("Nenhuma lista encontrada");
    }
}
