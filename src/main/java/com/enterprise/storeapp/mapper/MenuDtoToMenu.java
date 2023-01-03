package com.enterprise.storeapp.mapper;

import com.enterprise.storeapp.dto.MenuDto;
import com.enterprise.storeapp.entities.Category;
import com.enterprise.storeapp.entities.Menu;
import com.enterprise.storeapp.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuDtoToMenu implements IMapper<MenuDto, Menu>{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Menu map(MenuDto in) {
        Menu menu = new Menu();
        menu.setName(in.getName());
        menu.setCategory(in.getCategory());
        menu.setSubmenuList(in.getSubmenuList());
        return menu;
    }
}
