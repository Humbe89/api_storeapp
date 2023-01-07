package com.enterprise.storeapp.repositories;

import com.enterprise.storeapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findByNameContaining(String name);

    public List<Product> findByNameIsContaining(String name);

    public List<Product> findByNameContains(String name);

    public List<Product> findByNameLike(String title);

    @Query("SELECT m FROM Product m WHERE m.name LIKE %:title%")
    List<Product> searchByTitleLike(@Param("title") String title);
}
