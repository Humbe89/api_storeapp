package com.enterprise.storeapp.dto;

import com.enterprise.storeapp.entities.Category;
import com.enterprise.storeapp.entities.Menu;
import com.enterprise.storeapp.entities.Submenu;
import lombok.Data;

import java.util.List;

@Data
public class MenuDto {

    private String name;

    private Category category;



    private List<Submenu> submenuList;


}
