package com.Projeto.Fenix.exceptions;

public class ShoppingListNotFound extends RuntimeException{
    public ShoppingListNotFound(){
        super("ShoppingList não encontrada");
    }
}
