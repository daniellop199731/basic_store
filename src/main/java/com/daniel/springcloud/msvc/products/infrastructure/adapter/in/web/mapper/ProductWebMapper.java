package com.daniel.springcloud.msvc.products.infrastructure.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import com.daniel.springcloud.msvc.products.domain.model.Product;
import com.daniel.springcloud.msvc.products.infrastructure.adapter.in.web.dto.ProductWebDto;

@Component
public class ProductWebMapper {

    public Product toModel(ProductWebDto productDto){
        if(productDto == null){
            return null;
        }

        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setCreateAt(productDto.getCreateAt());
        return product;
    }

    public ProductWebDto toDto(Product product){
        if(product == null){
            return null;
        }

        ProductWebDto productDto = new ProductWebDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setStock(product.getStock());        
        productDto.setCreateAt(product.getCreateAt());
        return productDto;
    }
    
}
