package com.enterprise.storeapp.services;

import com.enterprise.storeapp.dto.ProductDto;
import com.enterprise.storeapp.dto.SubmenuDto;
import org.springframework.http.ResponseEntity;

public interface SubmenuService {

    public ResponseEntity<?> findAllSubmenu();

    public ResponseEntity<?> findSubmenuById(Long id);

    public ResponseEntity<?> createSubmenu(SubmenuDto submenuDto);

    public ResponseEntity<?> updateSubmenu(SubmenuDto submenuDto, Long id);

    public ResponseEntity<?> deleteSubmenu(Long id);
}
