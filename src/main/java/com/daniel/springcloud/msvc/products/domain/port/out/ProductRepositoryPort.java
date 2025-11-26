package com.daniel.springcloud.msvc.products.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.daniel.springcloud.msvc.products.domain.model.Product;

public interface ProductRepositoryPort {
    
    List<Product> findAll() throws Exception;
    Optional<Product> findById(Long id) throws Exception;
    Product save(Product product) throws Exception;
    void deleteById(Long id) throws Exception;
    boolean existsById(Long id) throws Exception;

    Optional<Product> findByName(String name) throws Exception;
}
