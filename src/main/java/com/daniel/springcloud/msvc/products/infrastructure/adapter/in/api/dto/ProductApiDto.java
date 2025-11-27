package com.daniel.springcloud.msvc.products.infrastructure.adapter.in.api.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductApiDto {
    
    private Long id;

    @NotNull(message = "El campo 'name' es requerido")
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Size(min = 4,max = 150, message = "El nombre del producto debe tener entre 4 y 150 caracteres")
    private String name;

    @NotNull(message = "El campo 'price' es requerido")
    @Positive(message = "El precio del producto debe ser un valor mayor a cero")
    @Max(value = 100000000000000L, message = "El precio del producto no puede ser mayor a 10 billones")
    private Double price;

    @PositiveOrZero(message = "El stock del producto no debe ser negativo")
    private Integer stock;

    private Integer port;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;
    
}
