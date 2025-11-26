package com.daniel.springcloud.msvc.products.application.useCase;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.springcloud.msvc.products.application.utils.ResponseGenericObject;
import com.daniel.springcloud.msvc.products.domain.model.Product;
import com.daniel.springcloud.msvc.products.domain.port.in.ProductUseCase;
import com.daniel.springcloud.msvc.products.domain.port.out.ProductRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductApplicationService implements ProductUseCase  {

    private final ProductRepositoryPort productRepository;

    private ResponseGenericObject<List<Product>> productListObj = new ResponseGenericObject<>();
    private ResponseGenericObject<Product> productObj = new ResponseGenericObject<>();
    
    private String message;

    @Override
    @Transactional(readOnly = true)
    public ResponseGenericObject<List<Product>> getAllProducts() {        
        message = "No se encontraron productos";
        try{
            List<Product> products = productRepository.findAll();
            if(!products.isEmpty()){
                message = "Productos encontrados exitosamente";
            }
            productListObj.setAsSuccessful(message, products);
        } catch (Exception e){
            message = "Error en la ejecucion del proceso: " + e.getMessage();
            productListObj.setAsWithErrors(message);
        }
        return productListObj;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseGenericObject<Product> getProductById(Long id) {
        message = "No se encontró el producto con ID: " + id;
        try{
            Optional<Product> product = productRepository.findById(id);
            if(!product.isPresent()){
                productObj.setAsNotSuccessful(message);
                return productObj;
            }
            message = "Producto encontrado";
            productObj.setAsSuccessful(message, product.get());
        } catch (Exception e){
            message = "Error en la ejecucion del proceso: " + e.getMessage();
            productObj.setAsWithErrors(message);            
        }
        return productObj;
    }

    @Override
    @Transactional
    public ResponseGenericObject<Product> createProduct(Product product) {
        try{
            Product savedProduct = productRepository.save(product);
            if(savedProduct.getId() != null){
                message = "Producto creado exitosamente";
                productObj.setAsSuccessful(message, savedProduct);
            } else {
                message = "No se pudo crear el producto";
                productObj.setAsNotSuccessful(message);
            }
        } catch (Exception e){
            message = "Error en la ejecucion del proceso: " + e.getMessage();
            productObj.setAsWithErrors(message); 
        }
        return productObj;
    }

    @Override
    @Transactional
    public ResponseGenericObject<Product> updateProduct(Long id, Product product) {
        try{
            if(id <= 0){
                message = "El ID del producto no es válido";
                productObj.setAsNotSuccessful(message);
                return productObj;
            }

            if(product == null){
                message = "Los datos del productos no son válidos";
                productObj.setAsNotSuccessful(message);
                return productObj;                
            }

            if(!productRepository.existsById(id)){
                message = "No se encontró el producto con ID: " + id;
                productObj.setAsNotSuccessful(message);
                return productObj;
            }

            product.setId(id);
            Product savedProduct = productRepository.save(product);
            if(savedProduct.getId() != null){
                message = "Producto actualizado exitosamente";
                productObj.setAsSuccessful(message, savedProduct);
            } else {
                message = "No se pudo actualizar el producto";
                productObj.setAsNotSuccessful(message);
            }            
        } catch (Exception e){
            message = "Error en la ejecucion del proceso: " + e.getMessage();
            productObj.setAsWithErrors(message);            
        }

        return productObj;
    }

    @Override
    @Transactional
    public ResponseGenericObject<Product> deleteProduct(Long id) {
        try{
            if(id <= 0){
                message = "El ID del producto no es válido";
                productObj.setAsNotSuccessful(message);
                return productObj;
            }
            Optional<Product> product = productRepository.findById(id);
            if(!product.isPresent()){
                message = "No se encontró el producto con ID: " + id;
                productObj.setAsNotSuccessful(message);
                return productObj;
            }
            productRepository.deleteById(id);
            message = "El producto: " + product.get().getName() + " con ID: " + id + " fue eliminado exitosamente";
            productObj.setAsSuccessful(message, product.get());
        } catch (Exception e){
            message = "Error en la ejecucion del proceso: " + e.getMessage();
            productObj.setAsWithErrors(message);            
        }
        return productObj;
    }
    
}
