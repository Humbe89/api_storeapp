package com.enterprise.storeapp.repositories;

import com.enterprise.storeapp.entities.Submenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmenuRepository  extends JpaRepository<Submenu, Long> {
}
