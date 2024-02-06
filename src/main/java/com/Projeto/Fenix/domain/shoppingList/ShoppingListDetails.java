package com.Projeto.Fenix.domain.shoppingList;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "shopping_list_details")
public class ShoppingListDetails {

    @Column(name = "id_list")
    private String listId;

    @Column(name = "id_item")
    private String itemId;

    @Column(name = "quantity")
    private double itemQuantity;
}
