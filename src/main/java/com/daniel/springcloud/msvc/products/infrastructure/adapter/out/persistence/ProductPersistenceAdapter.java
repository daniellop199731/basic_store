package com.daniel.springcloud.msvc.products.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.daniel.springcloud.msvc.products.domain.model.Product;
import com.daniel.springcloud.msvc.products.domain.port.out.ProductRepositoryPort;
import com.daniel.springcloud.msvc.products.infrastructure.adapter.out.persistence.entity.ProductEntity;
import com.daniel.springcloud.msvc.products.infrastructure.adapter.out.persistence.mapper.ProductMapper;
import com.daniel.springcloud.msvc.products.infrastructure.adapter.out.persistence.repository.ProductJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductRepositoryPort{

    private final ProductJpaRepository repository;
    private final ProductMapper mapper;

    @Override
    public List<Product> findAll() throws Exception {
        List<ProductEntity> productsEntities = repository.findAll();
        return productsEntities.stream().map(mapper::toModel).toList();
    }

    @Override
    public Optional<Product> findById(Long id) throws Exception{
        Optional<ProductEntity> productEntity = repository.findById(id);
        return productEntity.map(mapper::toModel);
    }

    @Override
    public Product save(Product product) throws Exception{
        ProductEntity productEntity = mapper.toEntity(product);
        return mapper.toModel(repository.save(productEntity));
    }

    @Override
    public void deleteById(Long id) throws Exception{
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) throws Exception{
        return this.findById(id).isPresent();
    }

    @Override
    public Optional<Product> findByName(String name) throws Exception {
        Optional<ProductEntity> productEntity = repository.findByName(name);
        return productEntity.map(mapper::toModel);
    }
    
}