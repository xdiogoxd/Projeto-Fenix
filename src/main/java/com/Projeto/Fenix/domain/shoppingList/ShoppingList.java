package com.Projeto.Fenix.domain.shoppingList;

import com.Projeto.Fenix.domain.items.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor



@Entity
@Table(name = "shopping_list")
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_list")
    private String listId;

    @Column(name = "list_name")
    private String listName;

    @Column(name = "description")
    private String listDescription;

    @Column(name = "list_image_url")
    private String listImage;

    @Column(name = "items")
    private List<Item> listItems;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "goal_date")
    private Date goalDate;



}
