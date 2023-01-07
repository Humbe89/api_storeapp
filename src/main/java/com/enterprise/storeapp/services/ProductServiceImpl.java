package com.enterprise.storeapp.services;

import com.enterprise.storeapp.dto.ProductDto;
import com.enterprise.storeapp.entities.Product;
import com.enterprise.storeapp.mapper.ProductDtoToProduct;
import com.enterprise.storeapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductDtoToProduct iMapperProduct;

    @Override
    public ResponseEntity<?> findAllProducts() {
        Map<String, Object> response = new HashMap<>();
        List<Product> productList = null;
        try {
           productList = this.productRepository.findAll();
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (productList.isEmpty()){
            response.put("Message", "Products is Empty");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        response.put("Message", "Products search Successfully");
        response.put("Products", productList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllProductsPages(Pageable pageable) {
        Map<String, Object> response = new HashMap<>();
        Page<Product> productList = null;
        try {
            productList = this.productRepository.findAll(pageable);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (productList.isEmpty()){
            response.put("Message", "Products is Empty");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        response.put("Message", "Products search Successfully");
        response.put("Products", productList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findProductById(Long id) {
        Map<String, Object> response = new HashMap<>();
        Product product = null;
        try {
            product = this.productRepository.findById(id).orElse(null);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (product == null){
            response.put("Message", "This product is not in the Database");
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("Message", "Product search Successfully");
        response.put("Product", product);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> createProduct(ProductDto productDto) {
        Map<String, Object> response = new HashMap<>();
        Product productAux = null;
        Product product = (Product) iMapperProduct.map(productDto);
        try {
            productAux = this.productRepository.save(product);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message", "Product insert Successfully");
        response.put("Product", product);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateProduct(ProductDto productDto, Long id) {
        Map<String, Object> response = new HashMap<>();
        Product product = (Product) this.iMapperProduct.map(productDto);
        Product productAux = null;
        Product productAux1 = null;
        try {
            productAux = this.productRepository.findById(id).orElse(null);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (productAux == null){
            response.put("Message", "This product is not in the DataBase");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        productAux.setName(product.getName());
        productAux.setDescription(product.getDescription());
        productAux.setPrice(product.getPrice());
        productAux.setProductstatus(product.getProductstatus());
        productAux.setMinStock(product.getMinStock());
        productAux.setActive(product.isActive());
        productAux.setAmount(product.getAmount());
        productAux.setDiscount(product.getDiscount());
        productAux.setCategory(product.getCategory());

        productAux1 = this.productRepository.save(productAux);
        response.put("Message", "Product update Successfully");
        response.put("Product", productAux1);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> deleteProduct(Long id) {
        Product product = null;
       product = this.productRepository.findById(id).orElse(null);
        Map<String, Object> response = new HashMap<>();
        if (product == null){
            response.put("Message", "This product is not in the Database");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        try {
            this.productRepository.deleteById(id);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("Message", "Product delete Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> upload(MultipartFile file, Long id) {
        Map<String, Object> response = new HashMap<>();
        Product product = productRepository.findById(id).orElse(null);

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

            String namePhotoAnterior = product.getPhoto();
            if (namePhotoAnterior != null && namePhotoAnterior.length() > 0){
               Path routePhotoAnterior = Paths.get("uploads") .resolve(namePhotoAnterior).toAbsolutePath();
               File filePhotoAnterior = routePhotoAnterior.toFile();
               if(filePhotoAnterior.exists() && filePhotoAnterior.canRead()){
                   filePhotoAnterior.delete();
               }
            }

            product.setPhoto(nameFile);
            productRepository.save(product);
            response.put("Message", "Photo success");
            response.put("Message", product);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return this.productRepository.findByNameContaining(name);
    }

    @Override
    public List<Product> findByNameIsContaining(String name) {
        return this.productRepository.findByNameIsContaining(name);
    }

    @Override
    public List<Product> findByNameContains(String name) {
        return this.productRepository.findByNameContains(name);
    }

    @Override
    public List<Product> findByNameLike(String title) {
        return this.productRepository.findByNameLike(title);
    }

    @Override
    public List<Product> searchByTitleLike(String title) {
        return this.productRepository.searchByTitleLike(title);
    }
}
