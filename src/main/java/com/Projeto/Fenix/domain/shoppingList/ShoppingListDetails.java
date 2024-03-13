package com.Projeto.Fenix.domain.shoppingList;


import com.Projeto.Fenix.domain.items.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "shopping_list_details")
public class ShoppingListDetails {

    @Id
    @Column(name = "id_details")
    private UUID detailsId;

    @ManyToOne
    @JoinColumn(name = "id_list", referencedColumnName = "id_list")
    private ShoppingList listId;

    @ManyToOne
    @JoinColumn(name = "id_item", referencedColumnName = "id_item")
    private Item itemId;

    @Column(name = "quantity")
    private double itemQuantity;
}
