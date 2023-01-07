package com.enterprise.storeapp.services;

import com.enterprise.storeapp.dto.ProductDto;
import com.enterprise.storeapp.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    public ResponseEntity<?> findAllProducts();

    public ResponseEntity<?> findAllProductsPages(Pageable pageable );

    public ResponseEntity<?> findProductById(Long id);

    public ResponseEntity<?> createProduct(ProductDto productDto);

    public ResponseEntity<?> updateProduct(ProductDto productDto, Long id);

    public ResponseEntity<?> deleteProduct(Long id);

    public ResponseEntity<?> upload(MultipartFile file, Long id) throws IOException;

    public List<Product> findByNameContaining(String name);

    public List<Product> findByNameIsContaining(String name);

    public List<Product> findByNameContains(String name);

    public List<Product> findByNameLike(String title);

    List<Product> searchByTitleLike(String title);
}
