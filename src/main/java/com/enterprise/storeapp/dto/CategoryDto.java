package com.enterprise.storeapp.dto;

import com.enterprise.storeapp.entities.Menu;
import com.enterprise.storeapp.entities.Product;
import com.enterprise.storeapp.entities.Submenu;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {

    private String name;

    private String description;

    private String photo;

   private List<Product> productList;

   private Submenu submenu;

   private Menu menu;
}
