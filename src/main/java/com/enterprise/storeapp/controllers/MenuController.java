package com.enterprise.storeapp.controllers;

import com.enterprise.storeapp.dto.MenuDto;
import com.enterprise.storeapp.dto.SubmenuDto;
import com.enterprise.storeapp.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api_store")
@CrossOrigin(origins = {"http://localhost:4200"})
public class MenuController {

    @Autowired(required = true)
    private MenuService menuService;

    @GetMapping("/menus")
    public ResponseEntity<?> findAllProducts(){
        return this.menuService.findAllMenus();
    }

    @GetMapping("menus/{id}")
    public ResponseEntity<?> findProductById(@PathVariable(name = "id") Long id){
        return this.menuService.findMenuById(id);
    }


    @PostMapping("/menus")
    public ResponseEntity<?> createProduct(@RequestBody MenuDto menuDto){
        return this.menuService.createMenu(menuDto);
    }

    @PutMapping("/menus/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody MenuDto menuDto, @PathVariable(name = "id") Long id){
        return this.menuService.updateMenu(menuDto, id);
    }


    @DeleteMapping("/menus/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Long id){
        return this.menuService.deleteMenu(id);
    }

    @PostMapping("/menus/{id}")
    public ResponseEntity<?> addSubmenu(@RequestBody SubmenuDto submenuDto, @PathVariable(name = "id")Long id){
        return this.menuService.addSubmenu(submenuDto, id);
    }
}
