package com.enterprise.storeapp.services;

import com.enterprise.storeapp.dto.MenuDto;
import com.enterprise.storeapp.dto.SubmenuDto;
import com.enterprise.storeapp.entities.Menu;
import com.enterprise.storeapp.entities.Submenu;
import com.enterprise.storeapp.mapper.MenuDtoToMenu;
import com.enterprise.storeapp.mapper.SubmenuDtoToSubmenu;
import com.enterprise.storeapp.repositories.MenuRepository;
import com.enterprise.storeapp.repositories.SubmenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuDtoToMenu menuDtoToMenu;

    @Autowired
    private SubmenuDtoToSubmenu submenuDtoToSubmenu;

    @Autowired
    private SubmenuRepository submenuRepository;


    @Override
    public ResponseEntity<?> findAllMenus() {
        Map<String, Object> response = new HashMap<>();
        List<Menu> menus = null;
        try {
            menus = this.menuRepository.findAll();
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (menus.isEmpty()){
            response.put("Message", "Menus is Empty");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        response.put("Message", "Menus search Successfully");
        response.put("Menus", menus);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findMenuById(Long id) {
        Map<String, Object> response = new HashMap<>();
        Menu menu = null;
        try {
            menu = this.menuRepository.findById(id).orElse(null);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (menu == null){
            response.put("Message", "This menu is not in the Database");
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("Message", "Menu search Successfully");
        response.put("Menu", menu);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createMenu(MenuDto menuDto) {
        Map<String, Object> response = new HashMap<>();
        Menu menuAux = null;
        Menu menu = (Menu) menuDtoToMenu.map(menuDto);
        try {
            menuAux = this.menuRepository.save(menu);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message", "Menu insert Successfully");
        response.put("Menu", menuAux);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateMenu(MenuDto menuDto, Long id) {
        Map<String, Object> response = new HashMap<>();
        Menu menu = (Menu) this.menuDtoToMenu.map(menuDto);
        Menu menuAux = null;
        Menu menuAux1 = null;
        System.out.println("estoy antes de buscar el menu");
        try {
            menuAux = this.menuRepository.findById(id).orElse(null);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (menuAux == null){
            response.put("Message", "This menu is not in the DataBase");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if(menu.getCategory() != null){
           if(menuAux.getSubmenuList().size()>0){
               response.put("Message","Este menu ya posee Submenus Asociados");
               return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
           }
        }
        menuAux.setName(menu.getName());
        menuAux.setCategory(menu.getCategory());
        //menuAux.setSubmenuList(menu.getSubmenuList());

        menuAux1 = this.menuRepository.save(menuAux);
        response.put("Message", "Menu update Successfully");
        response.put("Menu", menuAux1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteMenu(Long id) {
        Optional<?> category = this.menuRepository.findById(id);
        Map<String, Object> response = new HashMap<>();
        if (category.isEmpty()){
            response.put("Message", "This menu is not in the Database");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        try {
            this.menuRepository.deleteById(id);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("Message", "Menu delete Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addSubmenu(SubmenuDto submenuDto, Long id) {
        Map<String, Object> response = new HashMap<>();
        Submenu submenuAux = null;
        boolean flag = false;
        Submenu submenu = submenuDtoToSubmenu.map(submenuDto);
        Menu menu = this.menuRepository.findById(id).orElse(null);
        submenu.setMenu(menu);
        submenuAux = this.submenuRepository.save(submenu);
        //menu.getSubmenuList().add(submenuAux);



        this.menuRepository.save(menu);
        response.put("Message", "Submenu add with exit");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
