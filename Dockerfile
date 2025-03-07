# Etapa 1: Build
FROM maven:3.8.6-amazoncorretto-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=builder /app/target/products-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
