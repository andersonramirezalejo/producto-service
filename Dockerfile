# Usa una imagen base oficial de OpenJDK para Java 17
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor
COPY target/Productos-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que se ejecuta tu aplicación Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]

# Si necesitas pasar variables de entorno específicas para Docker
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db_productos:5432/productosdb
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=user
ENV SERVICE_API_KEY=productos_secreta_12345