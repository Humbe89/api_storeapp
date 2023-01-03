package com.enterprise.storeapp.controllers;


import com.enterprise.storeapp.dto.SubmenuDto;
import com.enterprise.storeapp.services.SubmenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api_store")
@CrossOrigin(origins = {"http://localhost:4200"})
public class SubmenuController {

    @Autowired
    private SubmenuService submenuService;

    @GetMapping("/submenus")
    public ResponseEntity<?> findAllProducts(){
        return this.submenuService.findAllSubmenu();
    }

    @GetMapping("submenus/{id}")
    public ResponseEntity<?> findProductById(@PathVariable(name = "id") Long id){
        return this.submenuService.findSubmenuById(id);
    }


    @PostMapping("/submenus")
    public ResponseEntity<?> createProduct(@RequestBody SubmenuDto submenuDto){
        return this.submenuService.createSubmenu(submenuDto);
    }

    @PutMapping("/submenus/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody SubmenuDto submenuDto, @PathVariable(name = "id") Long id){
        return this.submenuService.updateSubmenu(submenuDto, id);
    }


    @DeleteMapping("/submenus/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Long id){
        return this.submenuService.deleteSubmenu(id);
    }
}
