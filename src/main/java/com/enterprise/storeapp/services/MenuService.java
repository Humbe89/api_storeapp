package com.enterprise.storeapp.services;

import com.enterprise.storeapp.dto.CategoryDto;
import com.enterprise.storeapp.dto.MenuDto;
import com.enterprise.storeapp.dto.SubmenuDto;
import org.springframework.http.ResponseEntity;

public interface MenuService {

    public ResponseEntity<?> findAllMenus();

    public ResponseEntity<?> findMenuById(Long id);

    public ResponseEntity<?> createMenu(MenuDto menuDto);

    public ResponseEntity<?> updateMenu(MenuDto menuDto, Long id);

    public ResponseEntity<?> deleteMenu(Long id);

    public ResponseEntity<?> addSubmenu(SubmenuDto submenuDto, Long id);
}
