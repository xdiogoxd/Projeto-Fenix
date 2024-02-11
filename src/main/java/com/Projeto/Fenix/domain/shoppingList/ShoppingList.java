package com.Projeto.Fenix.domain.shoppingList;

import com.Projeto.Fenix.domain.items.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter


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

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "goal_date")
    private Date goalDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private ListMembers members;

    @ManyToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private ListShare shareList;

    @OneToMany(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private List<ShoppingListDetails> itemsInList;


}
