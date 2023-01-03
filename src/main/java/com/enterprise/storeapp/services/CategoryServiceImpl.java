package com.enterprise.storeapp.services;

import com.enterprise.storeapp.dto.CategoryDto;
import com.enterprise.storeapp.entities.Category;
import com.enterprise.storeapp.entities.Product;
import com.enterprise.storeapp.mapper.CategoryDtoToCategory;
import com.enterprise.storeapp.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    CategoryDtoToCategory iMapperCategory;


    @Override
    public ResponseEntity<?> findAllCategories() {
        Map<String, Object> response = new HashMap<>();
        List<Category> categories = null;
        try {
            categories = this.categoryRepository.findAll();
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (categories.isEmpty()){
            response.put("Message", "Categories is Empty");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        response.put("Message", "Categories search Successfully");
        response.put("Categories", categories);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findCategoryById(Long id) {
        Map<String, Object> response = new HashMap<>();
        Category category = null;
        try {
            category = this.categoryRepository.findById(id).orElse(null);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (category == null){
            response.put("Message", "This category is not in the Database");
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("Message", "Category search Successfully");
        response.put("Category", category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createCategory(CategoryDto categoryDto) {
        Map<String, Object> response = new HashMap<>();
        Category categoryAux = null;
        Category category = (Category) iMapperCategory.map(categoryDto);
        try {
            categoryAux = this.categoryRepository.save(category);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message", "Category insert Successfully");
        response.put("Product", categoryAux);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateCategory(CategoryDto categoryDto, Long id) {
        Map<String, Object> response = new HashMap<>();
        Category category = (Category) this.iMapperCategory.map(categoryDto);
        Category categoryAux = null;
        Category categoryAux1 = null;
        try {
            categoryAux = this.categoryRepository.findById(id).orElse(null);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (categoryAux == null){
            response.put("Message", "This category is not in the DataBase");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        categoryAux.setName(category.getName());
        categoryAux.setDescription(category.getDescription());
        categoryAux.setProductList(category.getProductList());
        categoryAux.setSubmenu(category.getSubmenu());
        categoryAux.setMenu(category.getMenu());

        categoryAux1 = this.categoryRepository.save(categoryAux);
        response.put("Message", "Category update Successfully");
        response.put("Category", categoryAux1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long id) {
        Optional<?> category = this.categoryRepository.findById(id);
        Map<String, Object> response = new HashMap<>();
        if (category.isEmpty()){
            response.put("Message", "This category is not in the Database");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        try {
            this.categoryRepository.deleteById(id);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("Message", "Category delete Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> upload(MultipartFile file, Long id) {
        Map<String, Object> response = new HashMap<>();
        Category category = categoryRepository.findById(id).orElse(null);

        if(!file.isEmpty()){
            String nameFile = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replaceAll(" ", "");
            Path routeFile = Paths.get("uploads").resolve(nameFile).toAbsolutePath();

            try {
                Files.copy(file.getInputStream(), routeFile);
            } catch (IOException e) {
                response.put("Message", "Error to upload photo");
                response.put("Message", e.getLocalizedMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String namePhotoAnterior = category.getPhoto();
            if (namePhotoAnterior != null && namePhotoAnterior.length() > 0){
                Path routePhotoAnterior = Paths.get("uploads") .resolve(namePhotoAnterior).toAbsolutePath();
                File filePhotoAnterior = routePhotoAnterior.toFile();
                if(filePhotoAnterior.exists() && filePhotoAnterior.canRead()){
                    filePhotoAnterior.delete();
                }
            }

            category.setPhoto(nameFile);
            categoryRepository.save(category);
            response.put("Message", "Photo success");
            response.put("Message", category);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public List<Category> findByMenuIsNullAndSubmenuIsNull() {
        return this.categoryRepository.findByMenuIsNullAndSubmenuIsNull();
    }


}
