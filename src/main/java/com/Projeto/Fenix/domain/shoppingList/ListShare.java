package com.Projeto.Fenix.domain.shoppingList;


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
@Table(name = "list_share")
public class ListShare {

    @Id
    @Column(name = "id_share_list")
    private String ShareListId;

    @ManyToOne
    @JoinColumn(name = "id_list")
    private ShoppingList listId;

    @Column(name = "invite_type")
    private String inviteType;

    @Column(name = "share_code")
    private String shareCode;
}
