package com.Projeto.Fenix.domain.users;

import com.Projeto.Fenix.domain.shoppingList.ListMembers;
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
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private String userId;

    @Column(name = "username", unique = true)
    private String userUsername;

    @Column(name = "password")
    private String userPassword;

    @Column(name = "email", unique = true)
    private String userEmail;

    @Column(name = "role")
    private String userRole;

    @Column(name = "image_profile_url")
    private String userImage;

    @Column(name = "display_name")
    private String userDisplayName;

    @ManyToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private ListMembers memberOf;

    public Users(String username, String password, String userEmail) {
        this.userUsername = username;
        this.userPassword = password;
        this.userEmail = userEmail;
    }
}
