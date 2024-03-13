package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Category;
import com.Projeto.Fenix.exceptions.CategoryAlreadyExistException;
import com.Projeto.Fenix.exceptions.CategoryNotFoundException;
import com.Projeto.Fenix.repositories.CategoriesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriesService {

    @Autowired
    UserService userService;

    @Autowired
    UuidService uuidService;

    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    EntityManager entityManager;

    public Category addNewCategory(String categoryName, String categoryDescription,
                                   String categoryIcon) throws Exception {
        // Valida se o nome da categoria está disponível para uso
        if (validaCategoryName(categoryName)) {
            //Seta os atributos para a nova categoria e cria a categoria
            UUID theId = uuidService.generateUUID();

            Category theNewCategory = new Category();

            theNewCategory.setCategoryId(theId);
            theNewCategory.setCategoryName(categoryName);
            theNewCategory.setCategoryDescription(categoryDescription);
            theNewCategory.setCategoryIcon(categoryIcon);

            return categoriesRepository.save(theNewCategory);
        }else{
            throw new CategoryAlreadyExistException();
        }

    }

    public Category updateCategoryById(UUID categoryId, String categoryName, String categoryDescription, String categoryIcon) throws Exception {
        // carrega categoria
        Category updatedCategory = findCategoryById(categoryId);

        // checa se o nome da categoria foi atualizado e se foi se esse novo nome já existe no banco de dados
        if(validaCategoryName(categoryName) || updatedCategory.getCategoryName().equals(categoryName)){
            // atualiza os campos e salva no banco de dados

            updatedCategory.setCategoryName(categoryName);
            updatedCategory.setCategoryDescription(categoryDescription);
            updatedCategory.setCategoryIcon(categoryIcon);

            return categoriesRepository.save(updatedCategory);
        }
        else {
            throw new CategoryAlreadyExistException();
        }
    }

    public Category updateCategoryByName(String categoryName, String categoryDescription, String categoryIcon) throws Exception {
        // carrega categoria
        Category updatedCategory = findCategoryByName(categoryName);

        // checa se o nome da categoria foi atualizado e se foi se esse novo nome já existe no banco de dados
        if(validaCategoryName(categoryName) || updatedCategory.getCategoryName().equals(categoryName)){
            // atualiza os campos e salva no banco de dados

            updatedCategory.setCategoryName(categoryName);
            updatedCategory.setCategoryDescription(categoryDescription);
            updatedCategory.setCategoryIcon(categoryIcon);

            return categoriesRepository.save(updatedCategory);
        }
        else {
            throw new CategoryAlreadyExistException();
        }
    }

    public List<Category> listAllCategories(){

        TypedQuery<Category> theQuery = entityManager.createQuery(
                "FROM Category", Category.class);

        // Lista todos os itens do banco de dados
        try {
            return theQuery.getResultList();
        }catch (Exception e){
            throw new CategoryNotFoundException();
        }
    }

    public Category findCategoryById(UUID categoryId){
        TypedQuery<Category> theQuery = entityManager.createQuery(
                "FROM Category WHERE categoryId=:theId", Category.class);

        theQuery.setParameter("theId", categoryId);
        // Procura item por ID
        try {
            return theQuery.getSingleResult();
        }catch (Exception e){
            throw new CategoryNotFoundException();
        }
    }

    public Category findCategoryByName(String categoryName){
        TypedQuery<Category> theQuery = entityManager.createQuery(
                "FROM Category WHERE categoryName=:theName", Category.class);

        theQuery.setParameter("theName", categoryName);
        // Procura item por nome
        try {
            return theQuery.getSingleResult();
        }catch (Exception e){
            throw new CategoryNotFoundException();
        }
    }

    Boolean validaCategoryName(String categoryName){
        //Valida se o nome da categoria está disponível, false = já esta em uso, true = está disponível.
        try {
            findCategoryByName(categoryName);
            return false;
        }catch (Exception e){
            return true;
        }
    }

    public void deleteCategoryById(UUID categoryId) throws Exception {
        // instancia a categoria
        Category theCategory = findCategoryById(categoryId);

        //Deleta a categoria
        categoriesRepository.delete(theCategory);

    }

    public void deleteCategoryByName(String categoryName) throws Exception {
        // instancia a categoria
        Category theCategory = findCategoryByName(categoryName);

        //Deleta a categoria
        categoriesRepository.delete(theCategory);
    }

}
