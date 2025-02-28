# Usar Maven con JDK 21 para compilar
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar archivos necesarios
COPY pom.xml .
COPY src ./src

# Compilar el proyecto
RUN mvn clean package -DskipTests

# Usar JDK 21 para ejecutar la aplicación
FROM eclipse-temurin:21-jdk AS runtime

WORKDIR /app

# Copiar toda la carpeta target desde la fase de construcción
COPY --from=build /app/target ./target

# Exponer el puerto (ajústalo según la configuración de tu aplicación)
EXPOSE 35000

# Comando para ejecutar la aplicación con -cp
CMD ["java", "-cp", "target/classes", "Lab03.app"]
