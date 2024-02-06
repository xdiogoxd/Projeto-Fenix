package com.Projeto.Fenix.domain.items;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private String itemId;

    @Column(name = "name")
    private String itemName;

    @Column(name = "description")
    private String itemDescription;

    @Column(name = "image_url")
    private String itemImage;

    @Column(name = "category")
    private String itemCategory;

    @Column(name = "brand")
    private String itemBrand;
}
