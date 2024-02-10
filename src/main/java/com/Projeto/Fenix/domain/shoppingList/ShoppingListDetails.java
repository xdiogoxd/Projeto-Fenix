package com.Projeto.Fenix.domain.shoppingList;


import com.Projeto.Fenix.domain.items.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "shopping_list_details")
public class ShoppingListDetails {

    @ManyToMany
    @JoinColumn(name = "id_list")
    private ShoppingList listId;

    @ManyToMany
    @JoinColumn(name = "id_item")
    private Item itemId;

    @Column(name = "quantity")
    private double itemQuantity;
}
