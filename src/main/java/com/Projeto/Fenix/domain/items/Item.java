package com.Projeto.Fenix.domain.items;

import com.Projeto.Fenix.domain.shoppingList.ListShare;
import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.domain.shoppingList.ShoppingListDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "items")
public class Item {

    @Id
    @Column(name = "id_item")
    private UUID ItemId;

    @Column(name = "name")
    private String itemName;

    @Column(name = "description")
    private String itemDescription;

    @Column(name = "image_url")
    private String itemImage;

    @Column(name = "category")
    private String itemCategory;

    @Column(name = "brand")
    private String itemBrand;

}
