package com.Projeto.Fenix.domain.shoppingList;


import jakarta.persistence.*;

@Entity
@Table(name = "list_share")
public class ListShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_share_code")
    private String ShareCodeId;

    @ManyToMany
    @JoinColumn(name = "id_list")
    private ShoppingList listId;

    @Column(name = "invite_type")
    private String inviteType;

    @Column(name = "share_code")
    private String shareCode;
}
