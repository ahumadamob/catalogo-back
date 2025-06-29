# Catalogo Back

Proyecto de ejemplo con Spring Boot y arquitectura MVC para exponer servicios REST.

## Requisitos
- Java 17
- Maven
- MySQL en ejecución (configurado en `src/main/resources/application.properties`)

## Ejecución

Para compilar y ejecutar el proyecto use:
```bash
mvn spring-boot:run
```

El proyecto expone un endpoint REST en `/items`.

## Estructura de paquetes

- `entity` contiene las entidades JPA.
- `service` define las interfaces de servicio y en `service.jpa` se encuentran las implementaciones que usan JPA.
