package com.Projeto.Fenix.domain.shoppingList;

import com.Projeto.Fenix.domain.items.Item;
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
@Table(name = "templates_details")
public class TemplateDetails {

    @ManyToMany
    @JoinColumn(name = "id_template")
    private Templates templateId;

    @ManyToMany
    @JoinColumn(name = "id_item")
    private Item itemId;

    @Column(name = "quantity")
    private double quantity;
}
