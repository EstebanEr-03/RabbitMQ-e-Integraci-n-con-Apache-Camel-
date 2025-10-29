# 🚀 API REST de Gestión de Envíos - Apache Camel

## 📋 Descripción del Proyecto

Esta API REST implementa un sistema de gestión de envíos utilizando **Apache Camel 4.14.0** con **Spring Boot 3.5.7** y **Java 21**. La aplicación demuestra los principios del diseño RESTful y patrones de integración empresarial usando Enterprise Integration Patterns (EIP).

### 🎯 Características Principales

- ✅ **API REST completa** con operaciones CRUD para envíos
- ✅ **Documentación OpenAPI 3.0** generada automáticamente  
- ✅ **Apache Camel Routes** implementando patrones de integración
- ✅ **Spring Boot** para gestión de dependencias y configuración
- ✅ **Java 21 LTS** como plataforma base
- ✅ **Gradle** como herramienta de construcción
- ✅ **Health Check** endpoint para monitoreo
- ✅ **Logging estructurado** para trazabilidad

## 🏗️ Arquitectura

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Cliente REST  │───▶│  Apache Camel    │───▶│  Lógica de      │
│   (Postman/App) │    │  Routes Engine   │    │  Negocio        │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                                │
                        ┌──────────────────┐
                        │  Spring Boot     │
                        │  Web Server      │
                        │  (Tomcat:8081)   │
                        └──────────────────┘
```

### 🔧 Stack Tecnológico

| Componente | Tecnología | Versión |
|------------|------------|---------|
| **Lenguaje** | Java | 21.0.8 LTS |
| **Framework** | Spring Boot | 3.5.7 |
| **Integración** | Apache Camel | 4.14.0 |
| **Build Tool** | Gradle | 8.x |
| **Servidor Web** | Apache Tomcat | 10.1.48 |
| **Documentación** | OpenAPI | 3.0.0 |

## 📡 Endpoints de la API

### Base URL: `http://localhost:8081/camel`

| Método | Endpoint | Descripción | Código |
|--------|----------|-------------|--------|
| `GET` | `/health` | Health check del servicio | 200 |
| `GET` | `/envios` | Listar todos los envíos | 200 |
| `GET` | `/envios/{id}` | Obtener envío por ID | 200/404 |
| `POST` | `/envios` | Crear nuevo envío | 201 |
| `GET` | `/api-doc` | Documentación OpenAPI | 200 |

### 📋 Ejemplos de Uso

#### 1. Health Check
```bash
GET http://localhost:8081/camel/health

Response:
{
  "status": "UP",
  "timestamp": "2025-10-29T18:19:39Z",
  "service": "API de Envíos",
  "version": "1.0.0"
}
```

#### 2. Listar Envíos
```bash
GET http://localhost:8081/camel/envios

Response:
[
  {
    "id": "001",
    "destinatario": "Juan Pérez",
    "direccion": "Av. Amazonas 123, Quito",
    "estado": "En tránsito",
    "fechaCreacion": "2025-10-29T15:30:00Z"
  }
]
```

#### 3. Crear Envío
```bash
POST http://localhost:8081/camel/envios
Content-Type: application/json

{
  "destinatario": "Ana García",
  "direccion": "Av. La Prensa 789, Quito",
  "estado": "Registrado"
}

Response:
{
  "mensaje": "Envío registrado correctamente",
  "id": "004",
  "fechaCreacion": "2025-10-29T18:20:11Z"
}
```

## 🛠️ Instalación y Configuración

### ✅ Pre-requisitos

- **Java 21** (OpenJDK o Oracle JDK)
- **Git** para clonar el repositorio
- **Postman** (opcional) para pruebas

### 📥 Instalación

1. **Clonar el repositorio**
```bash
git clone https://github.com/EstebanEr-03/first-camel-project.git
cd first-camel-project
```

2. **Verificar Java 21**
```bash
java -version
# Debe mostrar: openjdk version "21.x.x"
```

3. **Compilar el proyecto**
```bash
./gradlew build -x test
```

4. **Ejecutar la aplicación**
```bash
./gradlew bootRun
```

5. **Verificar funcionamiento**
```bash
curl http://localhost:8081/camel/health
```

### ⚙️ Configuración

El archivo `application.properties` contiene la configuración principal:

```properties
# Servidor
server.port=8081

# Apache Camel
camel.main.name=CamelLab
camel.rest.component=servlet
camel.rest.binding-mode=json
camel.rest.context-path=/api

# Logging
logging.level.org.apache.camel=INFO
```

## 🧪 Pruebas

### 🚀 Ejecución Manual con PowerShell

```powershell
# Health Check
Invoke-RestMethod -Uri "http://localhost:8081/camel/health" -Method GET

# Listar envíos
Invoke-RestMethod -Uri "http://localhost:8081/camel/envios" -Method GET

# Obtener envío por ID
Invoke-RestMethod -Uri "http://localhost:8081/camel/envios/001" -Method GET

# Crear envío
Invoke-RestMethod -Uri "http://localhost:8081/camel/envios" -Method POST -ContentType "application/json" -Body '{"destinatario":"Test User","direccion":"Test Address","estado":"Registrado"}'
```

### 📮 Colección de Postman

Incluida en el proyecto: `API-Envios-Postman-Collection.json`

**Importar en Postman:**
1. Abrir Postman
2. File → Import
3. Seleccionar `API-Envios-Postman-Collection.json`
4. Ejecutar las pruebas

### 🧪 Validaciones Automáticas

Cada request en Postman incluye validaciones automáticas:
- ✅ Tiempo de respuesta < 2000ms
- ✅ Content-Type: application/json
- ✅ Códigos de estado exitosos (200/201)

## 📚 Documentación OpenAPI

### 🌐 Acceso a la Documentación

- **URL**: http://localhost:8081/camel/api-doc
- **Formato**: JSON OpenAPI 3.0
- **Generación**: Automática desde Camel Rest-DSL

### 📄 Esquemas Definidos

```yaml
components:
  schemas:
    Envio:
      type: object
      properties:
        id: { type: string }
        destinatario: { type: string }
        direccion: { type: string }
        estado: { type: string }
        fechaCreacion: { type: string, format: date-time }
```

## 🏛️ Patrones de Integración Implementados

### 🔄 Enterprise Integration Patterns (EIP)

1. **Message Router**
   - Enrutamiento basado en método HTTP
   - Implementado en `RestApiRoute.java`

2. **Content-Based Router**  
   - Filtrado por ID de envío
   - Respuestas diferentes según existencia

3. **Message Translator**
   - Transformación JSON a objetos Java
   - Serialización automática con Jackson

4. **Request-Reply**
   - Patrón síncrono para todas las operaciones
   - Respuestas inmediatas al cliente

### 🛠️ Componentes Apache Camel Utilizados

- **`camel-rest-starter`**: Framework REST
- **`camel-servlet-starter`**: Integración con servlets
- **`camel-jackson-starter`**: Serialización JSON
- **`camel-openapi-java-starter`**: Documentación automática

## 📁 Estructura del Proyecto

```
first-camel-project/
├── 📁 src/main/java/com/integracion/camel/first_camel_project/
│   ├── FirstCamelProjectApplication.java    # Aplicación principal
│   ├── FileRoute.java                       # Rutas de archivos (original)
│   └── RestApiRoute.java                    # Rutas REST (nueva)
├── 📁 src/main/resources/
│   ├── application.properties               # Configuración
│   └── openapi.yaml                        # Especificación OpenAPI
├── 📁 input/                               # Archivos de entrada (original)
├── 📁 output/                              # Archivos procesados (original)  
├── 📁 archived/                            # Archivos archivados (original)
├── API-Envios-Postman-Collection.json      # Colección de pruebas
├── build.gradle                            # Configuración de dependencias
└── README.md                               # Esta documentación
```

## 🔧 Desarrollo y Extensión

### 🆕 Agregar Nuevos Endpoints

1. **Editar** `RestApiRoute.java`
2. **Agregar** nueva definición REST:
```java
rest("/nuevo-endpoint")
    .get().to("direct:nueva-ruta")

from("direct:nueva-ruta")
    .setBody(constant("{"mensaje":"Nuevo endpoint"}"));
```

3. **Recompilar** y ejecutar
```bash
./gradlew bootRun
```

### 🗄️ Integración con Base de Datos

Para conectar con base de datos, agregar dependencias:

```gradle
implementation 'org.apache.camel.springboot:camel-sql-starter:4.14.0'
implementation 'com.h2database:h2'
```

### 🔒 Seguridad

Para agregar autenticación JWT:

```gradle
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'org.apache.camel.springboot:camel-jwt-starter:4.14.0'
```

## 🐳 Dockerización

### Dockerfile

```dockerfile
FROM openjdk:21-jdk-slim
COPY build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Comandos Docker

```bash
# Construir imagen
docker build -t api-envios-camel .

# Ejecutar contenedor
docker run -p 8081:8081 api-envios-camel
```

## 📊 Monitoreo y Logging

### 📋 Logs Estructurados

```bash
# Ver logs en tiempo real
tail -f logs/application.log

# Filtrar logs de Camel
grep "Apache Camel" logs/application.log
```

### 📈 Spring Boot Actuator

- **Health**: http://localhost:8081/actuator/health
- **Metrics**: http://localhost:8081/actuator/metrics
- **Info**: http://localhost:8081/actuator/info

## 🚀 Despliegue en Producción

### ☁️ Variables de Entorno

```bash
export SERVER_PORT=8080
export CAMEL_LOG_LEVEL=WARN
export JAVA_OPTS="-Xmx512m -Xms256m"
```

### 🔄 CI/CD Pipeline

```yaml
# .github/workflows/deploy.yml
name: Deploy API
on: [push]
jobs:
  deploy:
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: '21'
      - run: ./gradlew build
      - run: ./gradlew bootRun
```

## 🤝 Contribución

1. **Fork** el proyecto
2. **Crear** rama para feature (`git checkout -b feature/nueva-funcionalidad`)
3. **Commit** cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. **Push** a la rama (`git push origin feature/nueva-funcionalidad`)
5. **Crear** Pull Request

## 📞 Soporte y Contacto

- **📧 Email**: desarrollo@empresa.com
- **🐛 Issues**: https://github.com/EstebanEr-03/first-camel-project/issues
- **📖 Documentación**: Apache Camel Official Docs

## 📝 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

---

## 🎯 Conclusión

Esta implementación demuestra:

- ✅ **Aplicación completa** de principios RESTful
- ✅ **Integración empresarial** con Apache Camel
- ✅ **Documentación automática** con OpenAPI
- ✅ **Arquitectura moderna** con Java 21 + Spring Boot
- ✅ **Escalabilidad** y mantenibilidad del código
- ✅ **Cumplimiento** de estándares de la industria

**🎉 ¡API REST completamente funcional y lista para producción!**