package com.enterprise.storeapp.controllers;

import com.enterprise.storeapp.dto.CategoryDto;
import com.enterprise.storeapp.entities.Category;
import com.enterprise.storeapp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("api_store")
@CrossOrigin(origins = {"http://localhost:4200"})
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<?> findAllProducts(){
        return this.categoryService.findAllCategories();
    }

    @GetMapping("categories/{id}")
    public ResponseEntity<?> findProductById(@PathVariable(name = "id") Long id){
        return this.categoryService.findCategoryById(id);
    }


    @PostMapping("/categories")
    public ResponseEntity<?> createProduct(@RequestBody CategoryDto categoryDto){
        return this.categoryService.createCategory(categoryDto);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody CategoryDto categoryDto, @PathVariable(name = "id") Long id){
        return this.categoryService.updateCategory(categoryDto, id);
    }


    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Long id){
        return this.categoryService.deleteCategory(id);
    }

    @PostMapping("/categories/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("id")Long id) throws IOException {

        return this.categoryService.upload(file, id);
    }

    @GetMapping("/categories/upload/img/{namePhoto:.+}")
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

    @GetMapping("/categories/null")
    public ResponseEntity<?> findByMenuIsNullAndSubmenuIsNull(){
        List<Category> categories = null;
       categories =  this.categoryService.findByMenuIsNullAndSubmenuIsNull();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
