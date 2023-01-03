package com.enterprise.storeapp.services;

import com.enterprise.storeapp.dto.SubmenuDto;
import com.enterprise.storeapp.entities.Submenu;
import com.enterprise.storeapp.mapper.SubmenuDtoToSubmenu;
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
public class SubmenuServiceImpl implements SubmenuService{

    @Autowired
    private SubmenuRepository submenuRepository;

    @Autowired
    SubmenuDtoToSubmenu iMapperSubmenu;


    @Override
    public ResponseEntity<?> findAllSubmenu() {
        Map<String, Object> response = new HashMap<>();
        List<Submenu> submenuList = null;
        try {
            submenuList = this.submenuRepository.findAll();
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (submenuList.isEmpty()){
            response.put("Message", "Submenu is Empty");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        response.put("Message", "Submenu search Successfully");
        response.put("Submenu", submenuList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findSubmenuById(Long id) {
        Map<String, Object> response = new HashMap<>();
        Submenu submenu = null;
        try {
            submenu = this.submenuRepository.findById(id).orElse(null);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (submenu == null){
            response.put("Message", "This Submenu is not in the Database");
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("Message", "Submenu search Successfully");
        response.put("Submenu", submenu);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createSubmenu(SubmenuDto submenuDto) {
        Map<String, Object> response = new HashMap<>();
        Submenu submenuAux = null;
        Submenu submenu = (Submenu) iMapperSubmenu.map(submenuDto);
        try {
            submenuAux = this.submenuRepository.save(submenu);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message", "Submenu insert Successfully");
        response.put("Submenu", submenuAux);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateSubmenu(SubmenuDto submenuDto, Long id) {
        Map<String, Object> response = new HashMap<>();
        Submenu submenu = (Submenu) this.iMapperSubmenu.map(submenuDto);
        Submenu submenuAux = null;
        Submenu submenuAux1 = null;
        try {
            submenuAux = this.submenuRepository.findById(id).orElse(null);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (submenuAux == null){
            response.put("Message", "This submenu is not in the DataBase");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        submenuAux.setName(submenu.getName());
        submenuAux.setCategory(submenu.getCategory());
        submenuAux.setMenu(submenu.getMenu());

        submenuAux1 = this.submenuRepository.save(submenuAux);
        response.put("Message", "Submenu update Successfully");
        response.put("Submenu", submenuAux1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteSubmenu(Long id) {
        Optional<?> submenu = this.submenuRepository.findById(id);
        Map<String, Object> response = new HashMap<>();
        if (submenu.isEmpty()){
            response.put("Message", "This submenu is not in the Database");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        try {
            this.submenuRepository.deleteById(id);
        }catch (DataAccessException exception){
            response.put("Message", exception.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("Message", "Submenu delete Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
