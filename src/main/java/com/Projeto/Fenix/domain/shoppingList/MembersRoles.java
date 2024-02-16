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
@Table(name = "members_roles")
public class MembersRoles {

    @Id
    @Column(name = "id_role")
    private String roleId;

    @Column(name = "name")
    private String roleName;

    @Column(name = "description")
    private String roleDescription;
}
