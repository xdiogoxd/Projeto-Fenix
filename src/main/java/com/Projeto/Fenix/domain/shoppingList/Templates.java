package com.Projeto.Fenix.domain.shoppingList;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "templates")
public class Templates {

    @Id
    @Column(name = "id_template")
    private String templateId;

    @Column(name = "name")
    private String templateName;

    @Column(name = "description")
    private String templateDescription;

    @Column(name = "image")
    private String templateImage;

    @Column(name = "creationDate")
    private Date templateCreationDate;

    @Column(name = "visibility")
    private String templateVisibility;

}
