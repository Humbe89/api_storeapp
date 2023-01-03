package com.enterprise.storeapp.entities;

import com.enterprise.storeapp.enumeratives.ProductStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;


import java.io.Serializable;

@Entity
@Table(name = "products")
@Data
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;

    private String description;

    private double price;

    private int amount;

    private double discount;

    private ProductStatus productstatus;

    private int minStock;  //Cantidad minima del producto para que me notifique cuando este quedando poco

    private boolean active;

    private String photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "productList", "<%= otherEntityRelationshipNamePlural %>"})
    private Category category;



}
