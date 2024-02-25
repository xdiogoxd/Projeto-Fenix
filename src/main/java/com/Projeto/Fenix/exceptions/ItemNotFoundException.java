package com.Projeto.Fenix.exceptions;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(){
        super("Item não localizado");
    }
}