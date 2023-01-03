package com.enterprise.storeapp.services;

import com.enterprise.storeapp.dto.CategoryDto;
import com.enterprise.storeapp.entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    public ResponseEntity<?> findAllCategories();

    public ResponseEntity<?> findCategoryById(Long id);

    public ResponseEntity<?> createCategory(CategoryDto categoryDto);

    public ResponseEntity<?> updateCategory(CategoryDto categoryDto, Long id);

    public ResponseEntity<?> deleteCategory(Long id);

    public ResponseEntity<?> upload(MultipartFile file, Long id) throws IOException;

    public List<Category> findByMenuIsNullAndSubmenuIsNull();
}
