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

    public Category addNewCategory(String categoryName, String categoryDescription, String categoryIcon) throws Exception {
        //Valida se nenhum dos campos enviados foram nulos
        if(categoryName != null && categoryDescription != null && categoryIcon != null){
            //Valida se o nome já está sendo utilizado
            try {
                findCategoryByName(categoryName);
            }catch (Exception e){
                // Seta todos os atributos da categoria e cria o item
                UUID theId = uuidService.generateUUID();
                Category theNewCategory = new Category();
                theNewCategory.setCategoryId(theId);
                theNewCategory.setCategoryName(categoryName);
                theNewCategory.setCategoryDescription(categoryDescription);
                theNewCategory.setCategoryIcon(categoryIcon);

                //Salva categoria no banco de dados
                return categoriesRepository.save(theNewCategory);
            }
            //Caso o nome da categoria já esteja sendo utilizada, retorna exceção
            throw new CategoryAlreadyExistException();
        }
        throw new MissingFieldsException();
    }

    public Category updateCategoryById(UUID categoryId, String categoryName, String categoryDescription,
        //Valida se nenhum dos campos é nulo
                                       String categoryIcon) throws Exception {
        if(categoryId != null && categoryName != null && categoryDescription != null && categoryIcon != null){
            Category updatedCategory = new Category();
            try{
                updatedCategory = findCategoryById(categoryId);
                // atualiza os campos e salva no banco de dados
                updatedCategory.setCategoryName(categoryName);
                updatedCategory.setCategoryDescription(categoryDescription);
                updatedCategory.setCategoryIcon(categoryIcon);
            } catch (Exception e){
                //Caso a categoria não seja localizada lança exceção
                throw new CategoryNotFoundException();
            }
            try {
                Category theCategory = findCategoryByName(categoryName);
                //Valida se o nome da categoria está disponível
                if (theCategory.getCategoryId() == updatedCategory.getCategoryId()){
                    return categoriesRepository.save(updatedCategory);
                }
            } catch (CategoryNotFoundException e){
                return categoriesRepository.save(updatedCategory);
            }
            throw new CategoryAlreadyExistException();

        }
        throw new MissingFieldsException();
    }

    public Category updateCategoryByName(String categoryName, String categoryDescription,
                                         String categoryIcon) throws Exception {

        //Valida se nenhum dos campos é nulo
        if(categoryName != null && categoryDescription != null && categoryIcon != null){
            try {
                //Procura o item e caso encontre ele, atualiza os campos e salva
                Category updatedCategory = findCategoryByName(categoryName);
                updatedCategory.setCategoryName(categoryName);
                updatedCategory.setCategoryDescription(categoryDescription);
                updatedCategory.setCategoryIcon(categoryIcon);
                return categoriesRepository.save(updatedCategory);
            }catch (Exception e){
                throw new CategoryNotFoundException();
            }

        }
        throw new MissingFieldsException();

    }
    public Category findCategoryById(UUID categoryId){
        //Valida se o input não foi nulo
        if(categoryId != null){
            try {
                Category theCategory = categoriesRepository.findCategoryByCategoryId(categoryId);
                return theCategory;

            }catch (Exception e){
                throw new CategoryNotFoundException();
            }
        }
        throw new MissingFieldsException();
    }

    public Category findCategoryByName(String categoryName){

        if(categoryName != null){
            try {
                Category theCategory = categoriesRepository.findCategoryByCategoryName(categoryName);
                return theCategory;
            }catch (Exception E){
                throw new CategoryNotFoundException();
            }
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



    public void deleteCategoryById(UUID categoryId){
        if(categoryId != null){
            try {
                Category theCategory = findCategoryById(categoryId);
                //Deleta a categoria
                categoriesRepository.delete(theCategory);
            }catch (Exception e){
                throw new CategoryNotFoundException();
            }
        }else {
            throw new MissingFieldsException();
        }
    }

    public void deleteCategoryByName(String categoryName) {
        if(categoryName != null){
            try {
                Category theCategory = findCategoryByName(categoryName);
                //Deleta a categoria
                categoriesRepository.delete(theCategory);
            }catch (Exception e){
                throw new CategoryNotFoundException();
            }
        }else {
            throw new MissingFieldsException();
        }
    }

}
