FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copiar archivos del wrapper de Maven y la configuración pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Descargar dependencias para aprovechar la caché de Docker
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente y construir el jar omitiendo pruebas
COPY src ./src
RUN ./mvnw package -DskipTests

# ==========================================
# ETAPA 2: Runtime / Ejecución
# ==========================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Crear un usuario no-root por seguridad
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copiar el JAR generado en la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto configurado para la API
EXPOSE 8080




ENTRYPOINT ["java", "-jar", "app.jar"]




