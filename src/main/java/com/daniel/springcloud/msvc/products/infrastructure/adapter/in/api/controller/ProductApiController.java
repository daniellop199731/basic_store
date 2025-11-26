package com.daniel.springcloud.msvc.products.infrastructure.adapter.in.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.springcloud.msvc.products.application.utils.ResponseGenericObject;
import com.daniel.springcloud.msvc.products.domain.model.Product;
import com.daniel.springcloud.msvc.products.domain.port.in.ProductUseCase;
import com.daniel.springcloud.msvc.products.infrastructure.adapter.in.api.dto.ProductDto;
import com.daniel.springcloud.msvc.products.infrastructure.adapter.in.api.mapper.ProductWebMapper;

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
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductUseCase useCase;
    private final ProductWebMapper mapper;

    @GetMapping("")
    public ResponseEntity<ResponseGenericObject<List<ProductDto>>> getAllProducts() {        
        return buildResponseEntityList(useCase.getAllProducts());        
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGenericObject<ProductDto>> getProductById(@PathVariable("id") Long id) {
        return buildResponseEntity(useCase.getProductById(id));
    }

    @PostMapping("")
    public ResponseEntity<ResponseGenericObject<ProductDto>> create(@Valid @RequestBody ProductDto productDto) {
        return buildResponseEntity(useCase.createProduct(mapper.toModel(productDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseGenericObject<ProductDto>> update(@PathVariable("id") Long id, @Valid @RequestBody ProductDto productDto) {
        return buildResponseEntity(useCase.updateProduct(id, mapper.toModel(productDto)));                
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseGenericObject<ProductDto>> delete(@PathVariable("id") Long id){
        return buildResponseEntity(useCase.deleteProduct(id));
    }

    private ResponseEntity<ResponseGenericObject<List<ProductDto>>> buildResponseEntityList(ResponseGenericObject<List<Product>> responseGenObj){        
        if(responseGenObj.isWithErrors()){
            return ResponseEntity.internalServerError().body(
                new ResponseGenericObject<>(
                    responseGenObj.isSuccessful(),
                    responseGenObj.getMessage(),
                    null
                )
            );
        }
        if(!responseGenObj.isSuccessful()){
            return ResponseEntity.badRequest().body(
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

    private ResponseEntity<ResponseGenericObject<ProductDto>> buildResponseEntity(ResponseGenericObject<Product> responseGenObj){        
        if(responseGenObj.isWithErrors()){
            return ResponseEntity.internalServerError().body(
                new ResponseGenericObject<>(
                    responseGenObj.isSuccessful(),
                    responseGenObj.getMessage(),
                    null
                )
            );
        }
        if(!responseGenObj.isSuccessful()){
            return ResponseEntity.badRequest().body(
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
