package com.daniel.springcloud.msvc.products.infrastructure.adapter.in.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.springcloud.msvc.products.domain.port.in.ProductUseCase;
import com.daniel.springcloud.msvc.products.infrastructure.adapter.in.api.dto.ProductApiDto;
import com.daniel.springcloud.msvc.products.infrastructure.adapter.in.api.mapper.ProductApiMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private final ProductApiMapper mapper;

    @GetMapping("")
    public ResponseEntity<List<ProductApiDto>> getAllProducts() {
        List<ProductApiDto> products = useCase.getAllProducts().getObj().stream().map(mapper::toDto).toList();
        if(products == null){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(products);
               
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductApiDto> getProductById(@PathVariable("id") Long id) throws InterruptedException {

        //SIMULACION DE ERRORES
        //EXCEPCION FABRICADA
        if (id.equals(10L)){
            throw new IllegalStateException("Producto no encontrado");
        }

        //TIMEOUT FABRICADO
        if (id.equals(7L)){
            TimeUnit.SECONDS.sleep(5L);
        }
        //SIMULACION DE ERRORES

        ProductApiDto product = mapper.toDto(useCase.getProductById(id).getObj());
        if(product == null){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping("")
    public ResponseEntity<ProductApiDto> create(@Valid @RequestBody ProductApiDto productDto) {
        ProductApiDto product = mapper.toDto(useCase.createProduct(mapper.toModel(productDto)).getObj());
        if(product == null){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductApiDto> update(@PathVariable("id") Long id, @Valid @RequestBody ProductApiDto productDto) {
        ProductApiDto product = mapper.toDto(useCase.updateProduct(id, mapper.toModel(productDto)).getObj());
        if(product == null){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(product);                      
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductApiDto> delete(@PathVariable("id") Long id){
        ProductApiDto product = mapper.toDto(useCase.deleteProduct(id).getObj());
        if(product == null){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(product);        
    }    
            
}
