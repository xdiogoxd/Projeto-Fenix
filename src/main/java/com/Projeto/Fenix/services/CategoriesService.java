package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.items.Category;
import com.Projeto.Fenix.exceptions.CategoryAlreadyExistException;
import com.Projeto.Fenix.exceptions.CategoryNotFoundException;
import com.Projeto.Fenix.exceptions.MissingFieldsException;
import com.Projeto.Fenix.repositories.CategoriesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriesService {

    @Autowired
    UuidService uuidService;

    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    EntityManager entityManager;

    public Category addNewCategory(String categoryName, String categoryDescription,
                                   String categoryIcon) throws Exception {
        if(categoryName != null && categoryDescription != null && categoryIcon != null){
            try {
                findCategoryByName(categoryName);
            }catch (Exception e){

                UUID theId = uuidService.generateUUID();

                Category theNewCategory = new Category();

                theNewCategory.setCategoryId(theId);
                theNewCategory.setCategoryName(categoryName);
                theNewCategory.setCategoryDescription(categoryDescription);
                theNewCategory.setCategoryIcon(categoryIcon);
                return categoriesRepository.save(theNewCategory);
            }
            throw new CategoryAlreadyExistException();
            //Seta os atributos para a nova categoria e cria a categoria
        }
        throw new MissingFieldsException();

        // Valida se o nome da categoria está disponível para uso

    }

    public Category updateCategoryById(UUID categoryId, String categoryName, String categoryDescription,
                                       String categoryIcon) throws Exception {
        if(categoryId != null && categoryName != null && categoryDescription != null && categoryIcon != null){
            Category updatedCategory = findCategoryById(categoryId);
            // checa se o nome da categoria foi atualizado
            if(updatedCategory.getCategoryName().equals(categoryName)){
                // atualiza os campos e salva no banco de dados
                updatedCategory.setCategoryDescription(categoryDescription);
                updatedCategory.setCategoryIcon(categoryIcon);

                return categoriesRepository.save(updatedCategory);
            }
            else {
                try {
                    findCategoryByName(categoryName);
                    throw new CategoryAlreadyExistException();
                } catch (CategoryNotFoundException e){
                    updatedCategory.setCategoryName(categoryName);
                    updatedCategory.setCategoryDescription(categoryDescription);
                    updatedCategory.setCategoryIcon(categoryIcon);
                    return categoriesRepository.save(updatedCategory);
                }
            }
        }
        throw new MissingFieldsException();
    }

    public Category updateCategoryByName(String categoryName, String categoryDescription, String categoryIcon) throws Exception {

        if(categoryName != null && categoryDescription != null && categoryIcon != null){
            // carrega categoria
            Category updatedCategory = findCategoryByName(categoryName);

            // atualiza os campos e salva no banco de dados
            updatedCategory.setCategoryName(categoryName);
            updatedCategory.setCategoryDescription(categoryDescription);
            updatedCategory.setCategoryIcon(categoryIcon);

            return categoriesRepository.save(updatedCategory);
        }
        throw new MissingFieldsException();

    }

    public List<Category> listAllCategories(){

        // Lista todos os itens do banco de dados
        try {
            return categoriesRepository.findAllCategories();
        }catch (Exception e){
            throw new CategoryNotFoundException();
        }
    }

    public Category findCategoryById(UUID categoryId){

        if(categoryId != null){
            Category theCategory = categoriesRepository.findCategoryByCategoryId(categoryId);
            if (theCategory != null) {
                return theCategory;
            }
            else{
            throw new CategoryNotFoundException();
            }
        }else
        throw new MissingFieldsException();
    }

    public Category findCategoryByName(String categoryName){

        if(categoryName != null){
            Category theCategory = categoriesRepository.findCategoryByCategoryName(categoryName);
            if (theCategory != null) {
                return theCategory;
            }
            else{
                throw new CategoryNotFoundException();
            }
        }
        throw new MissingFieldsException();
//        TypedQuery<Category> theQuery = entityManager.createQuery(
//                "FROM Category WHERE categoryName=:theName", Category.class);
//
//        theQuery.setParameter("theName", categoryName);
        // Procura item por nome
    }

    public void deleteCategoryById(UUID categoryId){
        if(categoryId != null){
            // instancia a categoria
            Category theCategory = findCategoryById(categoryId);

            //Deleta a categoria
            categoriesRepository.delete(theCategory);
        }else {
            throw new MissingFieldsException();
        }

    }

    public void deleteCategoryByName(String categoryName) {
        if(categoryName != null){
            // instancia a categoria
            Category theCategory = findCategoryByName(categoryName);

            if(theCategory == null){
                throw new CategoryNotFoundException();
            }

            //Deleta a categoria
            categoriesRepository.delete(theCategory);
        }else {
            throw new MissingFieldsException();
        }
    }

}
