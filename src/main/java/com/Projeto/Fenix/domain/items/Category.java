package com.Projeto.Fenix.domain.items;


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
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "id_category")
    private UUID categoryId;

    @Column(name = "name")
    private String categoryName;

    @Column(name = "description")
    private String categoryDescription;

    @Column(name = "icon")
    private String categoryIcon;

}
