package com.enterprise.storeapp.dto;

import com.enterprise.storeapp.entities.Category;
import com.enterprise.storeapp.entities.Menu;
import lombok.Data;

@Data
public class SubmenuDto {

    private String name;

    private Category category;

    private Menu menu;

}
