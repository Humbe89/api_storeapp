package com.enterprise.storeapp.mapper;

import com.enterprise.storeapp.dto.SubmenuDto;
import com.enterprise.storeapp.entities.Submenu;
import org.springframework.stereotype.Component;

@Component
public class SubmenuDtoToSubmenu implements IMapper<SubmenuDto, Submenu> {
    @Override
    public Submenu map(SubmenuDto in) {
        Submenu submenu = new Submenu();
        submenu.setName(in.getName());
        submenu.setCategory(in.getCategory());
        submenu.setMenu(in.getMenu());
        return submenu;
    }
}
