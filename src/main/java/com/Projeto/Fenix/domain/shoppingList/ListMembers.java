package com.Projeto.Fenix.domain.shoppingList;

import com.Projeto.Fenix.domain.users.Users;
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
    private UUID listMembersId;

    @ManyToOne
    @JoinColumn(name = "id_list")
    private ShoppingList listId;

    @ManyToOne
    @JoinColumn(name = "id_member")
    private Users memberId;

    @Column(name = "list_role")
    private String listRole;

}
