package com.enterprise.storeapp.controllers;

import com.enterprise.storeapp.dto.ProductDto;
import com.enterprise.storeapp.entities.Product;
import com.enterprise.storeapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api_store")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> findAllProducts(){
       return this.productService.findAllProducts();
    }

    @GetMapping("/productspage/{page}")
    public ResponseEntity<?> findAllProductsPages(@PathVariable(name = "page") Integer page){
        PageRequest pageRequest = PageRequest.of(page, 5);
        return this.productService.findAllProductsPages(pageRequest);

    }

    @GetMapping("products/{id}")
    public ResponseEntity<?> findProductById(@PathVariable(name = "id") Long id){
        return this.productService.findProductById(id);
    }


    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto){
        return this.productService.createProduct(productDto);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto, @PathVariable(name = "id") Long id){
        return this.productService.updateProduct(productDto, id);
    }


    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Long id){
        return this.productService.deleteProduct(id);
    }

    @PostMapping("/products/upload")
    public ResponseEntity<?> upload(@RequestParam("file")MultipartFile file, @RequestParam("id")Long id) throws IOException {

      return this.productService.upload(file, id);
    }

    @GetMapping("/products/upload/img/{namePhoto:.+}")
    public ResponseEntity<Resource> watchPhoto(@PathVariable String namePhoto){

        Path routeFile = Paths.get("uploads").resolve(namePhoto).toAbsolutePath();
        Resource resource = null;

        try {
            resource = new UrlResource(routeFile.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if(!resource.exists() && !resource.isReadable()){
            throw new RuntimeException("No se pudo cargar la imagen");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        return new ResponseEntity<Resource>(resource,httpHeaders, HttpStatus.OK);
    }


}
