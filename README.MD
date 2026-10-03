# tuLlave Recharges API

API REST para la gestión de recargas digitales de la tarjeta tuLlave, construida con **Spring Boot 3**, **PostgreSQL** y **JWT** para autenticación.

---

## 🚀 Cómo ejecutar el proyecto

### Prerrequisitos
- Docker y Docker Compose
- Java 21 (solo para ejecución local sin Docker)
- Maven Wrapper (`./mvnw`) incluido en el repo

### Opción 1: Con Docker Compose (recomendado)

```bash
# Levantar todos los servicios (PostgreSQL + API)
docker-compose up --build

# La API estará disponible en: http://localhost:8080
# Swagger UI: http://localhost:8080/swagger-ui.html
```

Para detener los servicios:
```bash
docker-compose down

# Eliminar también los volúmenes persistentes
docker-compose down -v
```

### Opción 2: Ejecución local

```bash
# 1. Levantar solo PostgreSQL
docker-compose up -d postgres-db

# 2. Ejecutar la aplicación
./mvnw spring-boot:run
```

### Ejecutar las pruebas

```bash
./mvnw test
```

---

## 🏗️ Arquitectura del proyecto

El proyecto sigue los principios de **Arquitectura Hexagonal (Ports & Adapters)** combinada con **Clean Architecture**, lo que permite desacoplar el dominio de los detalles de infraestructura.

```
src/main/java/com/tullave/recharges/
│
├── domain/                    # Núcleo del negocio (sin dependencias externas)
│   ├── entities/              # Recharge (modelo de dominio)
│   ├── enums/                 # PymentMethod
│   ├── exceptions/            # Excepciones de negocio
│   └── services/              # Puertos (interfaces) de entrada/salida
│
├── application/               # Casos de uso (orquestación)
│   ├── RechargeSave
│   ├── RechargeGet
│   └── RechargeDelete
│
├── infrastructure/            # Adaptadores (detalles técnicos)
│   ├── adapter/               # Implementaciones de los puertos (JPA)
│   ├── api/controller/        # Adaptador de entrada REST
│   ├── config/                # OpenAPI
│   ├── dto/                   # Objetos de transferencia
│   ├── exception/             # GlobalExceptionHandler + ErrorResponse
│   ├── mapper/                # MapStruct (DTO <-> Entity)
│   └── security/              # JWT (filtro, provider, config)
│
└── repository/                # Repositorio Spring Data JPA
```

### Decisiones técnicas

| Decisión | Justificación |
|---|---|
| **Arquitectura Hexagonal** | Permite sustituir la BD o el framework sin tocar el dominio. |
| **MapStruct** | Mapeo DTO ↔ Entity en tiempo de compilación, sin reflexión. |
| **JWT stateless** | Escalabilidad horizontal sin sesiones en servidor. |
| **`@RestControllerAdvice`** | Centraliza el manejo de errores con respuestas uniformes (`ErrorResponse`). |
| **Docker multi-stage** | Imagen final ligera (solo JRE) y reproducible. |
| **Usuario no-root en Docker** | Buena práctica de seguridad en producción. |
| **PostgreSQL con healthcheck** | Garantiza que la API espere a que la BD esté lista. |

### Endpoints principales

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/v1/getRecharges` | Lista todas las recargas |
| POST | `/api/v1/recharges` | Crea una nueva recarga |
| DELETE | `/api/v1/recharges/{id}` | Elimina una recarga por ID |

Todos requieren header `Authorization: Bearer <JWT>`.

---

## 🐳 Dockerfile y docker-compose

### Dockerfile (multi-stage)

```dockerfile
# ETAPA 1: builder
FROM eclipse-temurin:21-jdk-alpine AS builder
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -B   # Cachea dependencias
COPY src ./src
RUN ./mvnw package -DskipTests        # Genera el jar

# ETAPA 2: runtime
FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser                          # Ejecuta como usuario no-root
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Ventajas del multi-stage:**
- La imagen final **no incluye Maven ni el código fuente**, solo el JRE y el jar.
- Reduce el tamaño de ~500MB a ~180MB.
- `dependency:go-offline` aprovecha la caché de capas: si solo cambia el código, no re-descarga dependencias.

### docker-compose.yml

Define **dos servicios**:

1. **`postgres-db`**: PostgreSQL 16 con volumen persistente (`postgres_data`) y un `healthcheck` que verifica con `pg_isready`.
2. **`recharge-api`**: Construye la imagen desde el Dockerfile, expone el puerto `8080` y espera a que la BD esté saludable mediante `depends_on: condition: service_healthy`.

**Variables de entorno inyectadas a la API:**
- `SPRING_DATASOURCE_URL`, `USERNAME`, `PASSWORD`
- `SPRING_JPA_HIBERNATE_DDL_AUTO=update`
- `SPRING_JPA_SHOW_SQL=true`

**Persistencia:** el volumen `postgres_data` mantiene los datos aunque se reinicie el contenedor.

---

## 🌿 Flujo de Git utilizado

Se trabajó con un flujo basado en **ramas por funcionalidad (feature branching)**, compatible con **Git Flow simplificado**:

```
main
 └── develop
      ├── feature/domain-entities
      ├── feature/application-services
      ├── feature/infrastructure-adapters
      ├── feature/rest-controller
      ├── feature/jwt-security
      └── feature/docker-setup
```

### Convenciones aplicadas

- **`main`**: rama estable, solo recibe merges desde `develop` cuando hay release.
- **`develop`**: rama de integración donde se fusionan las features.
- **`feature/*`**: una rama por cada capa o funcionalidad (dominio, aplicación, infraestructura, seguridad, Docker). Se nombran en minúsculas y con guiones.
- **Commits** con mensajes descriptivos en imperativo, por ejemplo:
    - `feat: agrega entidad Recharge en el dominio`
    - `feat: implementa adaptador JPA de recargas`
    - `fix: corrige validación de monto inválido`
    - `chore: configura docker-compose con healthcheck`

### Flujo típico

```bash
# 1. Crear rama de feature desde develop
git checkout develop
git checkout -b feature/nombre-feature

# 2. Desarrollar y commitear
git add .
git commit -m "feat: descripción del cambio"

# 3. Publicar y crear Pull Request hacia develop
git push origin feature/nombre-feature

# 4. Tras revisión, merge a develop
# 5. Al cerrar un ciclo, merge develop -> main y tag de versión
git checkout main
git merge develop
git tag -a v1.0.0 -m "Primera versión estable"
```

**Beneficios:** historial limpio, integración continua por capas, y posibilidad de revertir features de forma aislada.