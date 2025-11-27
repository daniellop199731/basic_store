package com.daniel.springcloud.msvc.products.application.useCase;

import java.util.List;
import java.util.Optional;

import org.springframework.core.env.Environment;
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
    private final Environment environment;

    private ResponseGenericObject<List<Product>> productListObj     = new ResponseGenericObject<>();
    private ResponseGenericObject<Product> productObj               = new ResponseGenericObject<>();
    
    private String message;

    private static final String DEFAULT_ERROR_MESSAGE               = "Error en la ejecucion del proceso: ";
    private static final String NOT_FOUND_PRODUCTS_MESSAGE          = "No se encontraron productos";
    private static final String FOUND_PRODUCTS_MESSAGE              = "Productos encontrados exitosamente";    
    private static final String FOUND_PRODUCT_MESSAGE               = "Producto encontrado";
    private static final String NOT_FOUND_PRODUCT_BY_ID_MESSAGE     = "No se encontró el producto con ID: ";
    private static final String PRODUCT_CREATED_SUCCESS_MESSAGE     = "Producto creado exitosamente";
    private static final String PRODUCT_CREATED_NOT_SUCCESS         = "No se pudo crear el producto";
    private static final String INVALID_PRODUCT_ID_MESSAGE          = "El ID del producto no es válido";
    private static final String INVALID_PRODUCT_DATA_MESSAGE        = "Los datos del productos no son válidos";
    private static final String PRODUCT_UPDATED_SUCCESS_MESSAGE     = "Producto actualizado exitosamente";
    private static final String PRODUCT_UPDATED_NOT_SUCCESS         = "No se pudo actualizar el producto";

    @Override
    @Transactional(readOnly = true)
    public ResponseGenericObject<List<Product>> getAllProducts() {        
        message = NOT_FOUND_PRODUCTS_MESSAGE;
        try{
            List<Product> products = (productRepository.findAll()).stream().map(product -> {
                product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
                return product;
            }).toList();
            if(!products.isEmpty()){
                message = FOUND_PRODUCTS_MESSAGE;
            }
            productListObj.setAsSuccessful(message, products);
        } catch (Exception e){
            message = DEFAULT_ERROR_MESSAGE + e.getMessage();
            productListObj.setAsWithErrors(message);
        }
        return productListObj;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseGenericObject<Product> getProductById(Long id) {
        message = NOT_FOUND_PRODUCT_BY_ID_MESSAGE + id;
        try{
            Optional<Product> product = productRepository.findById(id).map(product2 -> {
                product2.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
                return product2;                
            });
            if(!product.isPresent()){
                productObj.setAsNotSuccessful(message);
                return productObj;
            }
            message = FOUND_PRODUCT_MESSAGE;
            productObj.setAsSuccessful(message, product.get());
        } catch (Exception e){
            message = DEFAULT_ERROR_MESSAGE + e.getMessage();
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
                message = PRODUCT_CREATED_SUCCESS_MESSAGE;
                productObj.setAsSuccessful(message, savedProduct);
            } else {
                message = PRODUCT_CREATED_NOT_SUCCESS;
                productObj.setAsNotSuccessful(message);
            }
        } catch (Exception e){
            message = DEFAULT_ERROR_MESSAGE + e.getMessage();
            productObj.setAsWithErrors(message); 
        }
        return productObj;
    }

    @Override
    @Transactional
    public ResponseGenericObject<Product> updateProduct(Long id, Product product) {
        try{
            if(id <= 0){
                message = INVALID_PRODUCT_ID_MESSAGE;
                productObj.setAsNotSuccessful(message);
                return productObj;
            }

            if(product == null){
                message = INVALID_PRODUCT_DATA_MESSAGE;
                productObj.setAsNotSuccessful(message);
                return productObj;                
            }

            if(!productRepository.existsById(id)){
                message = NOT_FOUND_PRODUCT_BY_ID_MESSAGE + id;
                productObj.setAsNotSuccessful(message);
                return productObj;
            }

            product.setId(id);
            Product savedProduct = productRepository.save(product);
            if(savedProduct.getId() != null){
                message = PRODUCT_UPDATED_SUCCESS_MESSAGE;
                productObj.setAsSuccessful(message, savedProduct);
            } else {
                message = PRODUCT_UPDATED_NOT_SUCCESS;
                productObj.setAsNotSuccessful(message);
            }            
        } catch (Exception e){
            message = DEFAULT_ERROR_MESSAGE + e.getMessage();
            productObj.setAsWithErrors(message);            
        }

        return productObj;
    }

    @Override
    @Transactional
    public ResponseGenericObject<Product> deleteProduct(Long id) {
        try{
            if(id <= 0){
                message = INVALID_PRODUCT_ID_MESSAGE;
                productObj.setAsNotSuccessful(message);
                return productObj;
            }
            Optional<Product> product = productRepository.findById(id);
            if(!product.isPresent()){
                message = NOT_FOUND_PRODUCT_BY_ID_MESSAGE + id;
                productObj.setAsNotSuccessful(message);
                return productObj;
            }
            productRepository.deleteById(id);
            message = "El producto: " + product.get().getName() + " con ID: " + id + " fue eliminado exitosamente";
            productObj.setAsSuccessful(message, product.get());
        } catch (Exception e){
            message = DEFAULT_ERROR_MESSAGE + e.getMessage();
            productObj.setAsWithErrors(message);            
        }
        return productObj;
    }    
}
