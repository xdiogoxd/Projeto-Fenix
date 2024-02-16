package com.Projeto.Fenix.domain.shoppingList;

import com.Projeto.Fenix.domain.items.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter


@Entity
@Table(name = "shopping_list")
public class ShoppingList {

    @Id
    @Column(name = "id_list")
    private UUID listId;

    @Column(name = "list_name")
    private String listName;

    @Column(name = "description")
    private String listDescription;

    @Column(name = "list_image_url")
    private String listImage;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "goal_date")
    private Date goalDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private ListMembers members;

    @ManyToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private List<ListShare> shareList;

    @OneToMany(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private List<ShoppingListDetails> itemsInList;

    public ShoppingList(UUID listId, String listName) {
        this.listId = listId;
        this.listName = listName;
    }
}
