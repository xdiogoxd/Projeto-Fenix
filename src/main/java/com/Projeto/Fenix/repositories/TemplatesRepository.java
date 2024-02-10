package com.Projeto.Fenix.repositories;

import com.Projeto.Fenix.domain.shoppingList.Templates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplatesRepository extends JpaRepository<Templates, String> {

    List<Templates> listAllTemplatesByUser(String userId);
}
