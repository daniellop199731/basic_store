package com.daniel.springcloud.msvc.products.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.daniel.springcloud.msvc.products.domain.model.Product;
import com.daniel.springcloud.msvc.products.infrastructure.adapter.out.persistence.entity.ProductEntity;

@Component
public class ProductMapper {

    public Product toModel(ProductEntity productEntity){
        if(productEntity == null){
            return null;
        }

        Product product = new Product();
        product.setId(productEntity.getId());
        product.setName(productEntity.getName());
        product.setPrice(productEntity.getPrice());
        product.setStock(productEntity.getStock());
        product.setCreateAt(productEntity.getCreateAt());
        return product;
    }

    public ProductEntity toEntity(Product product){
        if(product == null){
            return null;
        }

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setStock(product.getStock());
        productEntity.setCreateAt(product.getCreateAt());
        return productEntity;
    }    
    
}
