package com.daniel.springcloud.msvc.products.infrastructure.adapter.in.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.springcloud.msvc.products.application.utils.ResponseGenericObject;
import com.daniel.springcloud.msvc.products.domain.model.Product;
import com.daniel.springcloud.msvc.products.domain.port.in.ProductUseCase;
import com.daniel.springcloud.msvc.products.infrastructure.adapter.in.web.dto.ProductWebDto;
import com.daniel.springcloud.msvc.products.infrastructure.adapter.in.web.mapper.ProductWebMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/web/products")
@RequiredArgsConstructor
public class ProductWebController {

    private final ProductUseCase useCase;
    private final ProductWebMapper mapper;

    @GetMapping("")
    public ResponseEntity<ResponseGenericObject<List<ProductWebDto>>> getAllProducts() {        
        return buildResponseEntityList(useCase.getAllProducts());        
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGenericObject<ProductWebDto>> getProductById(@PathVariable("id") Long id) {
        return buildResponseEntity(useCase.getProductById(id));
    }

    @PostMapping("")
    public ResponseEntity<ResponseGenericObject<ProductWebDto>> create(@Valid @RequestBody ProductWebDto productDto) {
        return buildResponseEntity(useCase.createProduct(mapper.toModel(productDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseGenericObject<ProductWebDto>> update(@PathVariable("id") Long id, @Valid @RequestBody ProductWebDto productDto) {
        return buildResponseEntity(useCase.updateProduct(id, mapper.toModel(productDto)));                
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseGenericObject<ProductWebDto>> delete(@PathVariable("id") Long id){
        return buildResponseEntity(useCase.deleteProduct(id));
    }

    private ResponseEntity<ResponseGenericObject<List<ProductWebDto>>> buildResponseEntityList(ResponseGenericObject<List<Product>> responseGenObj){        
        if(responseGenObj.isWithErrors()){
            return ResponseEntity.internalServerError().body(
                new ResponseGenericObject<>(
                    responseGenObj.isSuccessful(),
                    responseGenObj.getMessage(),
                    null
                )
            );
        }        
        return ResponseEntity.ok(
            new ResponseGenericObject<>(
                responseGenObj.isSuccessful(),
                responseGenObj.getMessage(),
                responseGenObj.getObj().stream().map(mapper::toDto).toList()
            )
        );        
    }

    private ResponseEntity<ResponseGenericObject<ProductWebDto>> buildResponseEntity(ResponseGenericObject<Product> responseGenObj){        
        if(responseGenObj.isWithErrors()){
            return ResponseEntity.internalServerError().body(
                new ResponseGenericObject<>(
                    responseGenObj.isSuccessful(),
                    responseGenObj.getMessage(),
                    null
                )
            );
        }       
        return ResponseEntity.ok(
            new ResponseGenericObject<>(
                responseGenObj.isSuccessful(),
                responseGenObj.getMessage(),
                mapper.toDto(responseGenObj.getObj())
            )
        );        
    }    
            
}
