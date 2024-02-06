package com.Projeto.Fenix.domain.shoppingList;

import com.Projeto.Fenix.domain.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "list_members")
public class ListMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_member_list")
    private String listMembersId;

    @Column(name = "id_list")
    private String listId;

    @Column(name = "id_member")
    private List<Users> memberId;

    @Column(name = "list_role")
    private String listRole;

}
