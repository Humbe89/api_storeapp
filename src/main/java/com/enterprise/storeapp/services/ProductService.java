package com.enterprise.storeapp.services;

import com.enterprise.storeapp.dto.ProductDto;
import com.enterprise.storeapp.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    public ResponseEntity<?> findAllProducts();

    public ResponseEntity<?> findAllProductsPages(Pageable pageable );

    public ResponseEntity<?> findProductById(Long id);

    public ResponseEntity<?> createProduct(ProductDto productDto);

    public ResponseEntity<?> updateProduct(ProductDto productDto, Long id);

    public ResponseEntity<?> deleteProduct(Long id);

    public ResponseEntity<?> upload(MultipartFile file, Long id) throws IOException;
}
