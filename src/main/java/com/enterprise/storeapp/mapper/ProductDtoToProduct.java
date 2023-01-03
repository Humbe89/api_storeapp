package com.enterprise.storeapp.mapper;

import com.enterprise.storeapp.dto.ProductDto;
import com.enterprise.storeapp.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoToProduct implements IMapper<ProductDto, Product>{


    @Override
    public Product map(ProductDto in) {
        Product product = new Product();
        product.setName(in.getName());
        product.setDescription(in.getDescription());
        product.setAmount(in.getAmount());
        product.setPrice(in.getPrice());
        product.setDiscount(in.getDiscount());
        product.setProductstatus(in.getProductstatus());
        product.setActive(in.isActive());
        product.setPhoto(in.getPhoto());
        product.setMinStock(in.getMinStock());
        product.setCategory(in.getCategory());

        return product;
    }
}
