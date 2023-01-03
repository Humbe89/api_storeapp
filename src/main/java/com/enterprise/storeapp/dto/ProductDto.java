package com.enterprise.storeapp.dto;

import com.enterprise.storeapp.entities.Category;
import com.enterprise.storeapp.enumeratives.ProductStatus;
import lombok.Data;



@Data
public class ProductDto {


    private String name;

    private String description;

    private double price;

    private int amount;

    private double discount;

    private ProductStatus productstatus;

    private int minStock;  //Cantidad minima del producto para que me notifique cuando este quedando poco

    private boolean active;

    private String photo;

    private Category category;
}
