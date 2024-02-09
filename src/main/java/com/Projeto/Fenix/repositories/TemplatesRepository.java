package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.shoppingList.Templates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplatesRepository extends JpaRepository<Templates, String> {

    Optional<Templates> createTemplate(String name);

    Optional<Templates> updateTemplate(Templates updatedTemplate);

    void deleteTemplateById (String theId);
}
