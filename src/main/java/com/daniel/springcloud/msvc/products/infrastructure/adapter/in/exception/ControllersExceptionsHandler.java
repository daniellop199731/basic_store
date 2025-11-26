package com.daniel.springcloud.msvc.products.infrastructure.adapter.in.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.daniel.springcloud.msvc.products.application.utils.ResponseGenericObject;

@RestControllerAdvice
public class ControllersExceptionsHandler {

    private ResponseGenericObject<Map<String, String>> errorObj = new ResponseGenericObject<>();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseGenericObject<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        errorObj.setAsNotSuccessful("Errores en la petición", errors);
        return ResponseEntity.badRequest().body(errorObj);
    }

}

