package com.Projeto.Fenix.domain.shoppingList;

public enum EnumShoppingListMethod {

    ADD("add"),
    REMOVE("remove");

    private String method;

    EnumShoppingListMethod(String method){
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
