package com.enterprise.storeapp.mapper;

import com.enterprise.storeapp.dto.CategoryDto;
import com.enterprise.storeapp.entities.Category;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CategoryDtoToCategory implements IMapper<CategoryDto, Category>{

    @Override
    public Category map(CategoryDto in) {
        Category category = new Category();
        category.setName(in.getName());
        category.setDescription(in.getDescription());
        category.setProductList(in.getProductList());
        category.setSubmenu(in.getSubmenu());
        category.setMenu(in.getMenu());
        category.setPhoto(in.getPhoto());
        return category;


    }
}
