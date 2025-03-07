# Product API

API REST para la gestión de productos, construida con **Spring Boot** y empaquetada en contenedores Docker.  
Incluye un contenedor **PostgreSQL** para la base de datos y otro para la aplicación.

## Características

- CRUD de productos: crear 
- Validación de datos de entrada.
- Manejo de excepciones con respuestas HTTP adecuadas.
- Documentación OpenAPI/Swagger.
- Contenedorización con Docker y orquestación con docker-compose.

## Arquitectura del Proyecto

La arquitectura se basa en un **layered approach** con responsabilidades bien definidas en cada capa, lo que facilita el mantenimiento, la escalabilidad y las pruebas unitarias. A continuación se describe la estructura:

- **Controller**:
    - Expone los endpoints REST utilizando `@RestController` y gestiona las peticiones HTTP.

- **Service**:
    - Contiene la lógica de negocio. El servicio está definido mediante una interfaz que especifica sus funcionalidades, y una clase de implementación que realiza la lógica concreta. Esto promueve el desacoplamiento y facilita las pruebas unitarias.

- **Repository**:
    - Es la capa de acceso a datos utilizando Spring Data JPA, encargada de realizar las operaciones CRUD contra la base de datos.

- **Entity**:
    - Representa las tablas de la base de datos a través de anotaciones JPA.

- **DTO/Response**:
    - Son objetos utilizados para transferir datos entre la API y la capa de negocio, evitando la exposición directa de las entidades.

- **Configuration**:
    - Incluye las clases que configuran aspectos como CORS, Swagger, y logging.

- **Exception**:
    - Se encarga del manejo centralizado de excepciones y de proporcionar respuestas de error personalizadas.


## 2. Decisiones Técnicas Tomadas

- **Spring Boot**: Framework predilecto aparte de acelerar la creación de aplicaciones Java basadas en Spring, con auto-configuración y arranque rápido.
- **Java 17**: Versión LTS que aprovecha las últimas mejoras del lenguaje.
- **Maven**: Herramienta de construcción y gestión de dependencias.
- **Spring Data JPA**: Simplifica el acceso a la base de datos y el CRUD de entidades.
- **PostgreSQL**: Base de datos relacional elegida.
- **Lombok** : Reduce el boilerplate (getters, setters, constructores) en entidades y DTOs.
- **Logback con SLF4J**: Sistema de logging avanzado para imprimir logs en consola con colores y almacenar logs en archivos rotados por día.
- **Validación** con `javax.validation` (por ejemplo, `@NotBlank`, `@Size`) para asegurar la calidad de los datos.
- **Swagger/OpenAPI**: Documentación automática de la API, facilitando la exploración y el uso de los endpoints.
---

## 3. Explicación del Dockerfile y docker-compose

### Dockerfile

Se basa en un **multi-stage build** para optimizar la imagen final:

```dockerfile
# Etapa 1: Build
FROM maven:3.8.6-amazoncorretto-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=builder /app/target/product-api-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

```
builder: Usa la imagen oficial de Maven para compilar el proyecto (mvn package).

runtime: Copia el .jar generado y expone el puerto 8080 para la API.

### Docker-compose.yml

Este proyecto utiliza un archivo `docker-compose.yml` para orquestar dos servicios principales:

-  **db**: Un contenedor de PostgreSQL para la base de datos.
    - Usa la imagen oficial de PostgreSQL (postgres:latest).
    - Levanta el contenedor con el nombre product-db.
    - Define las variables de entorno a partir de variables definidas en el archivo `.env`
        - POSTGRES_USER
        - POSTGRES_PASSWORD
        - POSTGRES_DB
    - Mapea el puerto 5432 para poder conectarnos localmente a la base de datos.
    - Asigna un volumen persistente (product_data) para guardar los datos en la ruta `/var/lib/postgresql/data`
- **product-api**: La aplicación de Spring Boot que expone la API de productos.
    - Construye la imagen de la aplicación usando el Dockerfile en la carpeta actual `(build: .)`
    - Expone el contenedor en el puerto `8080`
    - Depende del contenedor db para asegurar que la base de datos esté disponible antes de iniciar la aplicación.
    - Se inyectan los valores desde el archivo `.env`
        - SPRING_DATASOURCE_USERNAME
        - SPRING_DATASOURCE_PASSWORD
        - POSTGRES_DB


```yaml
services:
  db:
    image: postgres:latest
    container_name: product-db
    environment:
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
      POSTGRES_DB: ${POSTGRES_DB}
    ports:
      - "5432:5432"
    volumes:
      - product_data:/var/lib/postgresql/data

  product-api:
    build: .
    container_name: product-api
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/${POSTGRES_DB}
      SPRING_DATASOURCE_USERNAME: ${SPRING_DATASOURCE_USERNAME}
      SPRING_DATASOURCE_PASSWORD: ${SPRING_DATASOURCE_PASSWORD}
      SPRING_JPA_HIBERNATE_DDL_AUTO: update

volumes:
  product_data:
```

# Pasos para ejecutar con Docker Compose

1. Asegúrate de tener Docker y docker-compose instalados en tu sistema
2. Crea/edita el archivo .env con las variables adecuadas (usuario, contraseña, etc.).
3. Ejecuta el siguiente comando para construir y levantar los contenedores:
```bash
   docker-compose up --build
```
4. Verifica que ambos contenedores (product-db y product-api) estén corriendo, puedes revisar los logs con `docker-compose logs -f`
5. Accede a la aplicación:
-  API: http://localhost:8080/products
- Swagger UI: http://localhost:8080/swagger-ui/index.html


# Flujo de Git

En este proyecto, se manejan varias ramas de Git para organizar el desarrollo:

1. **master**
    - Es la rama principal (default).
    - Contiene el código estable, listo para producción.

2. **feature/docker**
    - Rama destinada a implementar y ajustar la configuración de Docker.
    - Aquí se trabajaron el `Dockerfile`, `docker-compose.yml` y variables de entorno, asegurando que la aplicación se contenedorizara adecuadamente.

3. **local-dev**
    - Rama utilizada para configuraciones o cambios específicos de entornos locales.
    - Permite mantener ajustes que no se desean en la rama principal (por ejemplo, configuraciones puntuales para pruebas).

4. **feature/readme**
    - Rama creada específicamente para construir y refinar el archivo README, agregando documentación y explicaciones detalladas sobre la arquitectura, las decisiones técnicas y el flujo de trabajo.
5. **feature/pagination**
   - Rama creada para agregar la funcionalidad de paginación en el listado de productos en el servidor.
       En esta branch se implementó la lógica para paginar la respuesta de la API, mejorando la eficiencia y estabilidad en la serialización de la información.

## Flujo de Trabajo

1. **Crear/Usar una rama específica** para la funcionalidad deseada (por ejemplo, `feature/docker` o `feature/readme`).
2. **Realizar commits semánticos** (`feat:`, `fix:`, `docs:`, etc.) documentando cada cambio.
3. **Mergear la rama** de funcionalidad hacia `master` una vez que la nueva característica esté finalizada y revisada.
4. **Eliminar la rama** temporal si ya no es necesaria, manteniendo así un historial limpio y claro.

Este proceso facilita la colaboración, la revisión de código y el control de versiones de forma ordenada.

