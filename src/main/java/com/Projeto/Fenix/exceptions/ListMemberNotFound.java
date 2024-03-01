package com.Projeto.Fenix.exceptions;

public class ListMemberNotFound extends RuntimeException {

    public ListMemberNotFound(){
        super("Member não encontrado");
    }
}