package com.enterprise.storeapp.repositories;

import com.enterprise.storeapp.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
