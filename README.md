# CHALLENGE TRANSACTIONS

### Tecnologías Utilizadas en su última versión
- Spring Boot 2.6.7
- Spring Data JPA
- REST
- Java 11
- Maven
- Lombok
- Base de datos H2
- MockMvc, para pruebas de integración
- Faker, para armado de mock de datos

###Documentación OPEN API - SWAGGER
Link: http://localhost:8080/swagger-ui/index.html

###Acceso a BD H2
Link: http://localhost:8080/h2-console

###Ejecución por Dockerfile
Dentro del proyecto 'transactions'
```
mvn clean package
docker build -t transactions:1.0 .
docker run -d -p 8080:8080 -t transactions:1.0
```

