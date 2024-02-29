package com.Projeto.Fenix.domain.shoppingList;

import com.Projeto.Fenix.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "list_members")
public class ListMembers {

    @Id
    @Column(name = "id_member_list")
    private UUID listMembersId;

    @ManyToOne
    @JoinColumn(name = "id_list")
    private ShoppingList listId;

    @ManyToOne
    @JoinColumn(name = "id_member")
    private User memberId;

    @Column(name = "list_role")
    private String listRole;

}
