# 🛍️ MSVC Products -- Microservicio de Productos

## 📌 Descripción del Proyecto

MSVC Products es un microservicio desarrollado con Spring Boot y
arquitectura hexagonal (Ports & Adapters). Su propósito es gestionar
productos, permitiendo operaciones como crear, consultar, actualizar,
listar y eliminar productos. El servicio expone endpoints REST y está
preparado para funcionar como parte de un ecosistema de microservicios.

## 🧱 Arquitectura del Proyecto

El proyecto sigue una arquitectura hexagonal, con dominio, aplicación e
infraestructura. Contiene controladores REST, adaptadores de
persistencia, entidades, DTOs y casos de uso.

## 🧰 Tecnologías Utilizadas

-   Java 17+
-   Spring Boot 3
-   Spring Web
-   Spring Data JPA
-   Hibernate
-   H2 / MySQL
-   Gradle
-   Lombok

## 📡 Documentación de la API

### GET /api/products

Retorna lista de productos.

### GET /api/products/{id}

Obtiene un producto por ID.

### POST /api/products

Crea un producto.

### PUT /api/products/{id}

Actualiza un producto.

### DELETE /api/products/{id}

Elimina un producto.

## 🛠 Configuración y Ejecución

    ./gradlew clean build
    ./gradlew bootRun

## 🔁 Instrucciones para regenerar este README

"ChatGPT, analiza nuevamente este proyecto (adjunto el ZIP actualizado)
y genera un README completo con: descripción, arquitectura, tecnologías
usadas, documentación de APIs, instrucciones de instalación, ejecución y
sección para rehacer el README."
