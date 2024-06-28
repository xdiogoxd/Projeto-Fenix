package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Category;
import com.Projeto.Fenix.exceptions.CategoryAlreadyExistException;
import com.Projeto.Fenix.exceptions.CategoryNotFoundException;
import com.Projeto.Fenix.exceptions.MissingFieldsException;
import com.Projeto.Fenix.repositories.CategoriesRepository;
import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;



@ExtendWith(MockitoExtension.class)
class CategoriesServiceTest {

    @Mock
    private UuidService uuidService;

    @Mock
    private CategoriesRepository categoriesRepository;

    @InjectMocks
    private CategoriesService categoriesService;

    private Category testCategory;

    @BeforeEach
    public void setup(){
        testCategory = new Category(
                "Cozinha",
                "Itens de cozinha",
                "CozinhaIcon"
        );
    }

    @DisplayName("Adiciona uma nova categoria com sucesso")
    @Test
    void addNewCategorySuccessful() throws Exception {

        given(uuidService.generateUUID()).willReturn(UUID.randomUUID());
        given(categoriesRepository.save(any(Category.class))).willReturn(testCategory);

        Category savedCategory = categoriesService.addNewCategory(
                testCategory.getCategoryName(),
                testCategory.getCategoryDescription(),
                testCategory.getCategoryIcon()
        );

        verify(categoriesRepository, times(1)).save(any());
        assertEquals(savedCategory.getCategoryName(), testCategory.getCategoryName());

    }

    @DisplayName("Falha ao adicionar uma categoria com o nome duplicado")
    @Test
    void addNewCategoryDuplicatedName() throws Exception {
        Category testCategory2 = new Category(
                "Cozinha",
                "Itens de cozinha",
                "CozinhaIcon"
        );

        given(categoriesRepository.findCategoryByCategoryName(anyString())).willReturn(testCategory2);

        assertThrows(CategoryAlreadyExistException.class, () -> {
            categoriesService.addNewCategory(
                testCategory.getCategoryName(),
                testCategory.getCategoryDescription(),
                testCategory.getCategoryIcon()
        );
        });

        verify(categoriesRepository, times(0)).save(any());


    }

    @DisplayName("Falha ao adicionar categoria faltando campo")
    @Test
    void addNewCategoryWithNullFields() throws Exception {

        assertThrows(MissingFieldsException.class, () -> {
            categoriesService.addNewCategory(
                    testCategory.getCategoryName(),
                    null,
                    testCategory.getCategoryIcon()
            );
        });

        verify(categoriesRepository, times(0)).save(any());

    }

    @DisplayName("Atualiza categoria por id com sucesso")
    @Test
    void updateCategoryByIdSuccess() throws Exception {
        testCategory.setCategoryId(UUID.randomUUID());

        given(categoriesRepository.findCategoryByCategoryId(testCategory.getCategoryId())).willReturn(testCategory);
        given(categoriesRepository.save(any(Category.class))).willReturn(testCategory);

        Category savedCategory = categoriesService.updateCategoryById(
                testCategory.getCategoryId(),
                testCategory.getCategoryName(),
                testCategory.getCategoryDescription(),
                testCategory.getCategoryIcon()
        );

        verify(categoriesRepository, times(1)).save(any());
        assertEquals(savedCategory.getCategoryName(), testCategory.getCategoryName());

    }

    @DisplayName("Atualiza categoria por id com sucesso com um nome diferente do original")
    @Test
    void updateCategoryByIdSuccessWithANewName() throws Exception {
        testCategory.setCategoryId(UUID.randomUUID());

        Category testCategory2 = new Category();

        testCategory2.setCategoryName("Category2");
        testCategory2.setCategoryDescription("Description Category2");
        testCategory2.setCategoryIcon("Icon");

        given(categoriesRepository.findCategoryByCategoryId(testCategory.getCategoryId())).willReturn(testCategory);
        given(categoriesRepository.save(any(Category.class))).willReturn(testCategory);


        Category savedCategory = categoriesService.updateCategoryById(
                testCategory.getCategoryId(),
                testCategory2.getCategoryName(),
                testCategory2.getCategoryDescription(),
                testCategory2.getCategoryIcon()
        );

        verify(categoriesRepository, times(1)).save(any());
        assertEquals(savedCategory.getCategoryName(), testCategory.getCategoryName());

    }

    @DisplayName("Tenta atualizar categoria por id com nome já em uso")
    @Test
    void updateCategoryByIdWithNameAlreadyInUse() throws Exception {
        testCategory.setCategoryId(UUID.randomUUID());

        Category testCategory2 = new Category();

        testCategory2.setCategoryId(UUID.randomUUID());
        testCategory2.setCategoryName("Category2");
        testCategory2.setCategoryDescription("Description Category2");
        testCategory2.setCategoryIcon("Icon");

        given(categoriesRepository.findCategoryByCategoryId(testCategory.getCategoryId())).willReturn(testCategory);
        given(categoriesRepository.findCategoryByCategoryName(testCategory2.getCategoryName())).willReturn(testCategory2);

        assertThrows(CategoryAlreadyExistException.class, () -> {
            categoriesService.updateCategoryById(
                    testCategory.getCategoryId(),
                    testCategory2.getCategoryName(),
                    testCategory2.getCategoryDescription(),
                    testCategory2.getCategoryIcon()
            );
        });

        verify(categoriesRepository, times(0)).save(any());

    }

    @DisplayName("Tenta atualizar categoria por id com campos nulos")
    @Test
    void updateCategoryByWithMissingFields() throws Exception {
        testCategory.setCategoryId(UUID.randomUUID());

        assertThrows(MissingFieldsException.class, () -> {
            categoriesService.updateCategoryById(
                    testCategory.getCategoryId(),
                    testCategory.getCategoryName(),
                    null,
                    testCategory.getCategoryIcon()
            );
        });

        verify(categoriesRepository, times(0)).save(any());

    }

    @DisplayName("Atualiza categoria por nome com sucesso")
    @Test
    void updateCategoryByName() throws Exception {
        testCategory.setCategoryId(UUID.randomUUID());

        testCategory.setCategoryId(UUID.randomUUID());

        given(categoriesRepository.findCategoryByCategoryName(testCategory.getCategoryName())).willReturn(testCategory);
        given(categoriesRepository.save(any(Category.class))).willReturn(testCategory);

        Category savedCategory = categoriesService.updateCategoryByName(
                testCategory.getCategoryName(),
                testCategory.getCategoryDescription(),
                testCategory.getCategoryIcon()
        );

        verify(categoriesRepository, times(1)).save(any());
        assertEquals(savedCategory.getCategoryName(), testCategory.getCategoryName());
    }

    @DisplayName("Tenta atualizar categoria por nome com campos nulos")
    @Test
    void updateCategoryMissingFields() {

        assertThrows(MissingFieldsException.class, () -> {
            categoriesService.updateCategoryById(
                    testCategory.getCategoryId(),
                    testCategory.getCategoryName(),
                    null,
                    testCategory.getCategoryIcon()
            );
        });

        verify(categoriesRepository, times(0)).findCategoryByCategoryName(any());
        verify(categoriesRepository, times(0)).save(any());
    }

    @DisplayName("Retorna todas as categorias com sucesso")
    @Test
    void listAllCategoriesSuccess() {

        List<Category> testCategories = new ArrayList<>();
        testCategories.add(testCategory);

        given(categoriesRepository.findAllCategories()).willReturn(testCategories);

        List<Category> theCategories = categoriesService.listAllCategories();

        verify(categoriesRepository, times(1)).findAllCategories();
    }


    @DisplayName("Lista todas categorias com zero resultados")
    @Test
    void listAllCategoriesWithNoResults() {

        given(categoriesRepository.findAllCategories()).willThrow(CategoryNotFoundException.class);

        assertThrows(CategoryNotFoundException.class, () -> {
            categoriesService.listAllCategories();
        });
        verify(categoriesRepository, times(1)).findAllCategories();
    }

    @DisplayName("Procura categoria por ID com sucesso")
    @Test
    void findCategoryByIdSuccess() {
        testCategory.setCategoryId(UUID.randomUUID());


        given(categoriesRepository.findCategoryByCategoryId(any())).willReturn(testCategory);

        Category theCategory = categoriesService.findCategoryById(testCategory.getCategoryId());

        verify(categoriesRepository, times(1)).findCategoryByCategoryId(any());
        assertEquals(theCategory.getCategoryName(), testCategory.getCategoryName());
    }

    @DisplayName("Procura categoria por ID sem resultados")
    @Test
    void findCategoryByIdWithNoResults() {
        testCategory.setCategoryId(UUID.randomUUID());

        given(categoriesRepository.findCategoryByCategoryId(any())).willReturn(null);

        assertThrows(CategoryNotFoundException.class, () -> {
            categoriesService.findCategoryById(testCategory.getCategoryId());
        });

        verify(categoriesRepository, times(1)).findCategoryByCategoryId(any());
    }

    @DisplayName("Procura categoria por ID com campo nulo")
    @Test
    void findCategoryByIdWithNullId() {
        assertThrows(MissingFieldsException.class, () -> {
            categoriesService.findCategoryById(null);
        });

        verify(categoriesRepository,times(0)).findCategoryByCategoryId(any());
    }

    @DisplayName("Procura categoria por nome com sucesso")
    @Test
    void findCategoryByName() {
        given(categoriesRepository.findCategoryByCategoryName(any())).willReturn(testCategory);

        Category theCategory = categoriesService.findCategoryByName(testCategory.getCategoryName());

        verify(categoriesRepository, times(1)).findCategoryByCategoryName(any());
        assertEquals(theCategory.getCategoryName(), testCategory.getCategoryName());
    }

    @DisplayName("Procura categoria por nome sem resultados")
    @Test
    void findCategoryByNameWithNoResults() {
        given(categoriesRepository.findCategoryByCategoryName(any())).willReturn(null);

        assertThrows(CategoryNotFoundException.class, () -> {
            categoriesService.findCategoryByName(testCategory.getCategoryName());
        });

        verify(categoriesRepository, times(1)).findCategoryByCategoryName(any());
    }

    @DisplayName("Procura categoria por nome com campo nulo")
    @Test
    void findCategoryByNameWithNullFields() {
        assertThrows(MissingFieldsException.class, () -> {
            categoriesService.findCategoryById(null);
        });

        verify(categoriesRepository,times(0)).findCategoryByCategoryName(any());
    }

    @DisplayName("Deleta categoria por ID com sucesso")
    @Test
    void deleteCategoryByIdSuccess() throws Exception {
        testCategory.setCategoryId(UUID.randomUUID());

        given(categoriesRepository.findCategoryByCategoryId(any())).willReturn(testCategory);
        willDoNothing().given(categoriesRepository).delete(any());

        categoriesService.deleteCategoryById(testCategory.getCategoryId());

        verify(categoriesRepository, times(1)).delete(any());
    }
    @DisplayName("Tenta deletar categoria por ID com campo nulo")
    @Test
    void deleteCategoryByIdWithNullField() {
        assertThrows(MissingFieldsException.class, () -> {
            categoriesService.deleteCategoryById(null);
        });

        verify(categoriesRepository,times(0)).delete(any());
    }

    @DisplayName("Deleta categoria por nome com sucesso")
    @Test
    void deleteCategoryByNameSuccess() throws Exception {
        given(categoriesRepository.findCategoryByCategoryName(any())).willReturn(testCategory);

        categoriesService.deleteCategoryByName(testCategory.getCategoryName());

        verify(categoriesRepository, times(1)).delete(any());
    }

    @DisplayName("Tenta deletar categoria com nome não encontrado")
    @Test
    void deleteCategoryByNameWithNameNotFound() throws Exception {
        given(categoriesRepository.findCategoryByCategoryName(any())).willReturn(null);

        assertThrows(CategoryNotFoundException.class, () -> {
            categoriesService.deleteCategoryByName(testCategory.getCategoryName());
        });

        verify(categoriesRepository, times(0)).delete(any());
    }

    @DisplayName("Tenta deletar categoria com entrada nula")
    @Test
    void deleteCategoryByNameWithNullName() {
        assertThrows(MissingFieldsException.class, () -> {
            categoriesService.deleteCategoryByName(null);
        });

        verify(categoriesRepository,times(0)).delete(any());
    }
}