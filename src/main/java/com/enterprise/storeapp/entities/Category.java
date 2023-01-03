package com.enterprise.storeapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String photo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "category"})
    private List<Product> productList;

    @OneToOne(mappedBy = "category")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "category"})
    private Submenu submenu;

    @OneToOne(mappedBy = "category")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "category"})
    private Menu menu;


}
