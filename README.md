# CampoVivo Microservices — DSY1103

## Descripción

Sistema de microservicios para gestionar una cooperativa rural que ofrece hospedaje, actividades,
venta de productos y gestión de recursos comunitarios.

## Equipo

| Nombre | GitHub |
|--------|--------|
| Luis Fermin | @Lguille99 |
| Jeremy Ibañez | @Jereib |
| Vicente Krausse | @Mardram |

## Microservicios

| # | Microservicio | Puerto | Descripción |
|---|---------------|--------|-------------|
| 1 | ms-socios | 8081 | Gestión de socios |
| 2 | ms-hospedajes | 8082 | Gestión de hospedajes |
| 3 | ms-huespedes | 8083 | Gestión de huéspedes |
| 4 | ms-insumos | 8084 | Gestión de insumos |
| 5 | ms-actividades | 8085 | Gestión de actividades |
| 6 | ms-productos | 8086 | Gestión de productos |
| 7 | ms-ventas | 8087 | Gestión de ventas |
| 8 | ms-recursos | 8088 | Gestión de recursos |
| 9 | ms-fondo | 8089 | Gestión de deudas y validación de morosidad |
| 10 | ms-liquidaciones | 8090 | Liquidaciones de socios |

## Tecnologías

- Java 17
- Spring Boot 3.5.15
- Spring Cloud OpenFeign (comunicación entre microservicios)
- Spring Data JPA + H2 (base de datos en memoria)
- Spring Cloud Gateway (API Gateway en puerto 8080)
- SpringDoc OpenAPI (Swagger UI)
- JaCoCo (cobertura de pruebas)
- Docker + Docker Compose

## API Gateway

El gateway centraliza todas las rutas en el puerto `8080`:

| Ruta | Destino |
|------|---------|
| `/api/v1/socios/**` | ms-socios (8081) |
| `/api/v1/hospedajes/**` | ms-hospedajes (8082) |
| `/api/v1/huespedes/**` | ms-huespedes (8083) |
| `/api/v1/insumos/**` | ms-insumos (8084) |
| `/api/v1/actividades/**` | ms-actividades (8085) |
| `/api/v1/productos/**` | ms-productos (8086) |
| `/api/v1/ventas/**` | ms-ventas (8087) |
| `/api/v1/recursos/**` | ms-recursos (8088) |
| `/api/v1/deudas/**` | ms-fondo (8089) |
| `/api/v1/liquidaciones/**` | ms-liquidaciones (8090) |

Ejemplo de uso:

```bash
curl http://localhost:8080/api/v1/socios
curl http://localhost:8080/api/v1/recursos
curl http://localhost:8080/api/v1/liquidaciones/1
```

## Swagger UI

Cada microservicio expone su documentación OpenAPI:

| Microservicio | Swagger UI |
|---------------|------------|
| ms-socios | http://localhost:8081/swagger-ui.html |
| ms-hospedajes | http://localhost:8082/swagger-ui.html |
| ms-huespedes | http://localhost:8083/swagger-ui.html |
| ms-insumos | http://localhost:8084/swagger-ui.html |
| ms-actividades | http://localhost:8085/swagger-ui.html |
| ms-productos | http://localhost:8086/swagger-ui.html |
| ms-ventas | http://localhost:8087/swagger-ui.html |
| ms-recursos | http://localhost:8088/swagger-ui.html |
| ms-fondo | http://localhost:8089/swagger-ui.html |
| ms-liquidaciones | http://localhost:8090/swagger-ui.html |

## Ejecución local (Maven)

Cada microservicio se ejecuta de forma independiente:

```bash
# 1. Compilar y ejecutar pruebas
cd ms-recursos && ./mvnw clean test

# 2. Ejecutar el microservicio
cd ms-recursos && ./mvnw spring-boot:run

# 3. Ejecutar todos los microservicios (terminales separadas)
cd ms-socios && ./mvnw spring-boot:run
cd ms-hospedajes && ./mvnw spring-boot:run
cd ms-huespedes && ./mvnw spring-boot:run
cd ms-insumos && ./mvnw spring-boot:run
cd ms-actividades && ./mvnw spring-boot:run
cd ms-productos && ./mvnw spring-boot:run
cd ms-ventas && ./mvnw spring-boot:run
cd ms-recursos && ./mvnw spring-boot:run
cd ms-fondo && ./mvnw spring-boot:run
cd ms-liquidaciones && ./mvnw spring-boot:run
cd api-gateway/api-gateway && ./mvnw spring-boot:run
```

## Ejecución con Docker

```bash
# 1. Construir imágenes (ejecutar desde la raíz de cada microservicio)
cd ms-socios && ./mvnw clean package -DskipTests && docker build -t campo-vivo/socios .

# 2. Iniciar todos los servicios
docker compose up -d

# 3. Verificar estado
docker compose ps

# 4. Ver logs
docker compose logs -f

# 5. Detener
docker compose down
```

Para usar el perfil Docker (nombres de servicio en vez de localhost), las imágenes
se ejecutan automáticamente con el perfil `docker` activo si se define la variable
`SPRING_PROFILES_ACTIVE=docker`.

## Pruebas y cobertura

```bash
# Ejecutar pruebas de un microservicio
cd ms-recursos && ./mvnw clean test

# Ver reporte de cobertura (abrir en navegador)
open ms-recursos/target/site/jacoco/index.html

# Ejecutar pruebas de todos los microservicios
for d in ms-* ms.*/ms.*/ms.*; do
  if [ -f "$d/pom.xml" ]; then
    (cd "$d" && ./mvnw clean test 2>&1 | tail -5)
  fi
done
```

## Estado

En desarrollo
