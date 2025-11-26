package com.daniel.springcloud.msvc.products.infrastructure.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel.springcloud.msvc.products.infrastructure.adapter.out.persistence.entity.ProductEntity;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long>{

    Optional<ProductEntity> findByName(String name);
    
}
