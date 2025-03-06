# Product API

API REST para la gestión de productos, construida con **Spring Boot** y empaquetada en contenedores Docker.  
Incluye un contenedor PostgreSQL para la base de datos y otro para la aplicación.

## Características

- CRUD de productos: alta, baja, modificación y consulta.
- Validación de datos de entrada.
- Manejo de excepciones con respuestas HTTP adecuadas.
- Documentación OpenAPI/Swagger.
- Contenedorización con Docker y orquestación con docker-compose.

## Requisitos

- Java 17
- Maven 3.8+
- Docker y docker-compose

## Ejecución

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/product-api.git
   cd product-api
