package com.Projeto.Fenix.domain.shoppingList;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public enum ListMemberRoles {

    ADMIN("admin"),
    CO_ADMIN("coAdmin"),
    VISITOR("visitor");

    private String role;

    ListMemberRoles(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
