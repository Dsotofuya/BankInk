
# 1. Build de la aplicación
FROM maven:3.9.8-eclipse-temurin-21 AS builder

# 1.1. Seteo directorio de trabajo
WORKDIR /app

# 1.2 Copia y descarga las dependencias para optimizar rendimiento
# 1.2.1 Copia solo los archivos de configuración de la aplicación
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# 1.2.2. Pre-descarga las dependencias para que quede en caché
RUN ./mvnw dependency:go-offline

# 1.2.3. Copia el src del proyecto
COPY src src

# 1.3 Hace build de la aplicación
RUN ./mvnw clean package -DskipTests

# 2. Correr aplicación
FROM alpine/java:21.0.2-jdk

# 2.1 Seteo directorio de trabajo
WORKDIR /app

# 2.2 Copia desde builder (alpine/java:21.0.2-jdk) la aplicacion compilada
COPY --from=builder /app/target/*.jar app.jar

# 2.3 Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]