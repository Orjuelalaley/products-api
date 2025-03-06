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

## Execution

1. Clone the repository:
   ```bash
   git clone https://github.com/tu-usuario/product-api.git
   cd product-api

2. Build the project:
   ```bash
   mvn clean package
3. Run the application:
   ```bash
   java -jar target/product-api-1.0.0.jar
4. Access the API
   - API Endpoints: http://localhost:8080/products
   - Swagger UI: http://localhost:8080/swagger-ui/index.html
     
## Running with Docker
1. Ensure Docker and docker-compose are installed.
2. Build the Docker images:
   ```bash
   docker-compose up --build
3. Access the API
   - API Endpoints: http://localhost:8080/products
   - Swagger UI: http://localhost:8080/swagger-ui/index.html
4. stop the container:
    ```bash
    docker-compose down
    ```
