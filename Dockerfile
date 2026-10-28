
# ============================================================
# DOCKERFILE
# SISTEMA DE GESTION DE CITAS ODONTOLOGICAS
# Consultorio Odontologico Dra. Magda Ortiz (SGCO)
# ============================================================
#
# Este archivo permite construir y ejecutar el backend
# Spring Boot dentro de un contenedor Docker.
#
# Se utiliza una compilacion en dos etapas:
# 1. Compilar el proyecto Java con Maven.
# 2. Ejecutar el archivo JAR generado con Java 21.
#
# Las credenciales y configuraciones de produccion se
# proporcionan mediante variables de entorno en Render.
#
# ============================================================


# ============================================================
# ETAPA 1: COMPILACION DEL BACKEND
# ============================================================

FROM maven:3.9-eclipse-temurin-21 AS compilacion

# Directorio de trabajo dentro del contenedor.
WORKDIR /app

# Copia primero el archivo Maven para preparar dependencias.
COPY pom.xml .

# Copia el codigo fuente del proyecto.
COPY src ./src

# Compila Spring Boot y genera el archivo JAR.
# Las pruebas automatizadas se ejecutaran por separado.
RUN mvn -B clean package -DskipTests


# ============================================================
# ETAPA 2: EJECUCION DE SPRING BOOT
# ============================================================

FROM eclipse-temurin:21-jre

# Directorio donde se ejecutara la aplicacion.
WORKDIR /app

# Copia el JAR generado en la primera etapa.
COPY --from=compilacion /app/target/sgcodramagdaortiz-0.0.1-SNAPSHOT.jar app.jar

# Documenta el puerto predeterminado de Spring Boot.
# Render proporcionara el puerto mediante la variable PORT.
EXPOSE 8765

# Inicia el backend.
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
