package com.Projeto.Fenix.exceptions;

public class MissingFieldsException extends RuntimeException{
    public MissingFieldsException(){
        super("Algum ou alguns campos mandatórios não foram preenchidos");
    }
}
