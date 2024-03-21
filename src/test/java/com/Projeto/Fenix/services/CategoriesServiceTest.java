package com.Projeto.Fenix.services;

import com.Projeto.Fenix.FenixApplication;
import com.Projeto.Fenix.domain.items.Category;
import com.Projeto.Fenix.exceptions.CategoryAlreadyExistException;
import com.Projeto.Fenix.exceptions.MissingFieldsException;
import com.Projeto.Fenix.repositories.CategoriesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CategoriesServiceTest {

    @Mock
    UuidService uuidService;

    @Mock
    CategoriesRepository categoriesRepository;

    @Autowired
    @InjectMocks
    CategoriesService categoriesService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Adiciona uma categoria com sucesso")
    void addNewCategorySuccess() throws Exception {
        String categoryName = "Category";
        String categoryDescription = "Description";
        String categoryIcon = "Icon";

        categoriesService.addNewCategory(categoryName,categoryDescription,categoryIcon);

        verify(categoriesRepository, times(1)).save(any());

    }
//Voltarei neste tópico mais tarde, achei um pouco confuso, então vou estudar melhor para aplicar os testes unitários e de integração

//    @Test
//    @DisplayName("Tenta adicionar uma categoria com nome duplicado")
//    void addNewCategoryNameDuplicated() throws Exception {
//        String categoryName = "Category";
//        String categoryDescription = "Description";
//        String categoryIcon = "Icon";
//
//        when(categoriesService.validaCategoryName(categoryName)).thenReturn(false);
//
//
//        Exception thrown = Assertions.assertThrows(CategoryAlreadyExistException.class,() -> {
//            categoriesService.addNewCategory(categoryName,categoryDescription,categoryIcon);
//        });
//
//        Assertions.assertEquals("Nome indisponível", thrown.getMessage());
//    }
//
//    @Test
//    @DisplayName("Tenta adicionar uma categoria campo nulo")
//    void addNewCategoryMissingFields() throws Exception {
//        String categoryName = "Category";
//        String categoryDescription = "Description1";
//        String categoryIcon = new String();
//
//        Exception thrown = Assertions.assertThrows(MissingFieldsException.class,() -> {
//            categoriesService.addNewCategory(categoryName,categoryDescription,categoryIcon);
//        });
//
//        Assertions.assertEquals("Algum ou alguns campos mandatórios não foram preenchidos", thrown.getMessage());
//    }
//
//
//    @Test
//    @DisplayName("Atualiza uma categoria por Id com sucesso")
//    void updateCategoryByIdSuccessful() throws Exception {
//
//
//        String updatedCategoryName = "Category1";
//        String updatedCategoryDescription = "Description1";
//        String updatedCategoryIcon = "Icon1";
//
//        categoriesService.updateCategoryById(uuidService.generateUUID(), updatedCategoryName, updatedCategoryDescription,updatedCategoryIcon);
//
//        verify(categoriesRepository, times(1)).save(any());
//
//    }

//    @Test
//    @DisplayName("Tenta atualizar categoria com ID invalido")
//    void updateCategoryByIdNotFound() throws Exception {
//        UUID theId = uuidService.generateUUID();
//        String categoryName = "Category2";
//        String categoryDescription = "Description1";
//        String categoryIcon = "Icon1";
//
//        categoriesService.updateCategoryById(theId,categoryName,categoryDescription,categoryIcon);
//
//        assertThatException();
//    }
//
//    @Test
//    @DisplayName("Tenta atualizar categoria com campos não preenchidos")
//    void updateCategoryByIdMissingFields() throws Exception {
//
//
//        Category newCategory = new Category(uuidService.generateUUID(),"Category","Description","Icon");
//
//        String updatedCategoryName = "Category";
//        String updatedCategoryDescription = "Description1";
//        String updatedCategoryIcon = null;
//
//        categoriesService.updateCategoryById(newCategory.getCategoryId(), updatedCategoryName, updatedCategoryDescription,updatedCategoryIcon);
//
//        assertThatException();
//
//    }
//
//    @Test
//    @DisplayName("Tenta atualizar categoria com name duplicado")
//    void updateCategoryByIdNameAlreadyTaken() throws Exception {
//        String categoryName = "Category";
//        String categoryName2 = "Category2";
//        String categoryDescription = "Description";
//        String categoryIcon = "Icon";
//
//        Category newCategory = categoriesService.addNewCategory(categoryName,categoryDescription,categoryIcon);
//        categoriesService.addNewCategory(categoryName2,categoryDescription,categoryIcon);
//
//        String updatedCategoryName = "Category2";
//        String updatedCategoryDescription = "Description1";
//        String updatedCategoryIcon = "Icon1";
//
//        categoriesService.updateCategoryById(newCategory.getCategoryId(), updatedCategoryName, updatedCategoryDescription,updatedCategoryIcon);
//
//        assertThatException();
//
//        categoriesService.deleteCategoryByName(categoryName);
//
//    }
//
//    @Test
//    @DisplayName("Atualiza uma categoria por nome")
//    void updateCategoryByNameSuccessful() throws Exception {
//        String updatedCategoryName = "Category";
//        String updatedCategoryDescription = "Description1";
//        String updatedCategoryIcon = "Icon";
//
//        categoriesService.updateCategoryByName(updatedCategoryName, updatedCategoryDescription,updatedCategoryIcon);
//
//        categoriesService.deleteCategoryByName(updatedCategoryName);
//    }
//
//    @Test
//    @DisplayName("Tenta atualizar categoria com Nome invalido")
//    void updateCategoryByNameNotFound() throws Exception {
//        String categoryName = "RandomName";
//        String categoryDescription = "Description1";
//        String categoryIcon = "Icon1";
//
//        categoriesService.updateCategoryByName(categoryName,categoryDescription,categoryIcon);
//
//        assertThatException();
//    }
//
//    @Test
//    @DisplayName("Tenta atualizar categoria com campos não preenchidos")
//    void updateCategoryByNameMissingFields() throws Exception {
//        String categoryName = "Category";
//        String categoryDescription = "Description";
//        String categoryIcon = "Icon1";
//
//        categoriesService.addNewCategory(categoryName,categoryDescription,categoryIcon);
//
//        String updatedCategoryName = "Category";
//        String updatedCategoryDescription = "Description";
//        String updatedCategoryIcon = null;
//
//        categoriesService.updateCategoryByName(updatedCategoryName, updatedCategoryDescription,updatedCategoryIcon);
//
//        assertThatException();
//
//        categoriesService.deleteCategoryByName(categoryName);
//    }
//
//    @Test
//    @DisplayName("Tenta atualizar categoria com name duplicado")
//    void updateCategoryByNameAlreadyTaken() throws Exception {
//        Category newCategory = new Category(uuidService.generateUUID(),"Category","Description","Icon");
//        Category newCategory2 = new Category(uuidService.generateUUID(),"Category2","Description","Icon");
//
//        String updatedCategoryName = "Category2";
//        String updatedCategoryDescription = "Description";
//        String updatedCategoryIcon = "Icon";
//
//        categoriesService.updateCategoryById(newCategory.getCategoryId(), updatedCategoryName, updatedCategoryDescription,updatedCategoryIcon);
//
//        assertThatException();
//
//    }
//
//    @Test
//    @DisplayName("Lista todas categorias")
//    void listAllCategories() throws Exception {
//        Category newCategory = new Category(uuidService.generateUUID(),"Category","Description","Icon");
//
//        List<Category> theCategories = categoriesService.listAllCategories();
//
//        assertThat(theCategories).isNotEmpty();
//    }
//
//    @Test
//    @DisplayName("Localiza categoria por Id")
//    void findCategoryByIdSuccessful() throws Exception {
//        Category newCategory = new Category(uuidService.generateUUID(),"Category","Description","Icon");
//
//        Category foundCategory = categoriesService.findCategoryById(newCategory.getCategoryId());
//
//        assertThat(foundCategory).isNot(null);
//
//    }
//
//    @Test
//    @DisplayName("Falha em categoria por Id")
//    void findCategoryByIdNotFound(){
//        UUID theId = uuidService.generateUUID();
//        categoriesService.findCategoryById(theId);
//
//        assertThatException();
//    }
//
//    @Test
//    @DisplayName("Localiza categoria por nome")
//    void findCategoryByNameSuccess() throws Exception {
//        String categoryName = "Category";
//        String categoryDescription = "Description";
//        String categoryIcon = "Icon";
//
//        Category newCategory = categoriesService.addNewCategory(categoryName,categoryDescription,categoryIcon);
//
//        Category foundCategory = categoriesService.findCategoryByName(newCategory.getCategoryName());
//
//        assertThat(foundCategory).isNot(null);
//
//        categoriesService.deleteCategoryByName(categoryName);
//    }
//
//    @Test
//    @DisplayName("Falha ao localizar categoria por nome invalido")
//    void findCategoryByNameFailed() throws Exception {
//        String categoryName = "Category";
//
//        categoriesService.findCategoryByName(categoryName);
//
//        assertThatException();
//    }
//
//    @Test
//    @DisplayName("Deleta categoria por ID")
//    void deleteCategoryByIdSuccess() throws Exception {
//        Category newCategory = new Category(uuidService.generateUUID(),"Category","Description","Icon");
//
//        categoriesService.deleteCategoryById(newCategory.getCategoryId());
//
//        assertThat(newCategory).is(null);
//    }
//
//    @Test
//    @DisplayName("Falha ao deletar categoria por ID invalido")
//    void deleteCategoryByIdNotFound() throws Exception {
//        UUID theId = uuidService.generateUUID();
//
//        categoriesService.deleteCategoryById(theId);
//
//        assertThatException();
//    }
//
//
//    @Test
//    @DisplayName("Deleta categoria por nome")
//    void deleteCategoryByNameSuccess() throws Exception {
//        Category newCategory = new Category(uuidService.generateUUID(),"Category","Description","Icon");
//
//        categoriesService.deleteCategoryByName(newCategory.getCategoryName());
//
//        assertThat(newCategory).isNull();
//    }
//
//    @Test
//    @DisplayName("Falha ao deletar categoria por ID")
//    void deleteCategoryByNameNotFound() throws Exception {
//        String categoryName = "Category";
//
//        categoriesService.deleteCategoryByName(categoryName);
//
//        assertThatException();
//    }
}