package com.Projeto.Fenix.domain.user;

import com.Projeto.Fenix.domain.shoppingList.ListMembers;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "id_user")
    private UUID userId;

    @Column(name = "username", unique = true)
    private String userUsername;

    @Column(name = "password")
    private String userPassword;

    @Column(name = "email", unique = true)
    private String userEmail;

    @Enumerated(EnumType.STRING)
//    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "role")
    private UserRole userRole;

    @Column(name = "image_profile_url")
    private String userImage;

    @Column(name = "display_name")
    private String userDisplayName;

    @ManyToOne()
    @PrimaryKeyJoinColumn()
    private ListMembers memberOf;

    public User(String username, String password, String userEmail) {
        this.userUsername = username;
        this.userPassword = password;
        this.userEmail = userEmail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.userRole == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        else{
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));}
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userUsername;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
