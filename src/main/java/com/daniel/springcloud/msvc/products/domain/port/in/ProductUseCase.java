package com.daniel.springcloud.msvc.products.domain.port.in;

import java.util.List;

import com.daniel.springcloud.msvc.products.application.utils.ResponseGenericObject;
import com.daniel.springcloud.msvc.products.domain.model.Product;

public interface ProductUseCase {
    
    ResponseGenericObject<List<Product>> getAllProducts();
    ResponseGenericObject<Product> getProductById(Long id);
    ResponseGenericObject<Product> createProduct(Product product);
    ResponseGenericObject<Product> updateProduct(Long id, Product product);
    ResponseGenericObject<Product> deleteProduct(Long id);

}
