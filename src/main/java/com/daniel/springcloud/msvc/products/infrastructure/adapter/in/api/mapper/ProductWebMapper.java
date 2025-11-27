package com.daniel.springcloud.msvc.products.infrastructure.adapter.in.api.mapper;

import org.springframework.stereotype.Component;

import com.daniel.springcloud.msvc.products.domain.model.Product;
import com.daniel.springcloud.msvc.products.infrastructure.adapter.in.api.dto.ProductDto;

@Component
public class ProductWebMapper {

    public Product toModel(ProductDto productDto){
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

    public ProductDto toDto(Product product){
        if(product == null){
            return null;
        }

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setStock(product.getStock());
        productDto.setPort(product.getPort());
        productDto.setCreateAt(product.getCreateAt());
        return productDto;
    }
    
}
