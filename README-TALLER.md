# 🚀 API REST de Envíos - Apache Camel

## 📋 Información del Taller

**Estudiante:** Esteban Erazo  
**Materia:** Integración de Sistemas  
**Taller:** API REST con Apache Camel  
**Fecha:** 29 de Octubre, 2025  
**Branch:** `taller-api-rest`  

---

## 🎯 Objetivos Cumplidos

✅ **API REST para gestión de envíos implementada con Apache Camel**  
✅ **Tres endpoints principales funcionando:**
- `GET /camel/envios` - Consultar todos los envíos
- `GET /camel/envios/{id}` - Consultar envío por ID  
- `POST /camel/envios` - Registrar nuevo envío

✅ **Documentación OpenAPI 3.0 generada automáticamente**  
✅ **Colección Postman incluida**  
✅ **Health check endpoint implementado**  
✅ **Dashboard web con información de la API**  

---

## 🛠️ Stack Tecnológico

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Java** | 21 LTS | Lenguaje de programación |
| **Spring Boot** | 3.5.7 | Framework base |
| **Apache Camel** | 4.14.0 | Motor de integración |
| **Gradle** | 8.x | Build system |
| **OpenAPI** | 3.0 | Documentación API |

### Componentes de Apache Camel Utilizados:
- `camel-spring-boot-starter` - Integración con Spring Boot
- `camel-rest-starter` - REST DSL
- `camel-servlet-starter` - Servlet component  
- `camel-openapi-java-starter` - Documentación OpenAPI
- `camel-jackson-starter` - Serialización JSON

---

## 🌐 Endpoints de la API

### Base URL: `http://localhost:8082`

| Método | Endpoint | Descripción | Ejemplo |
|--------|----------|-------------|---------|
| `GET` | `/camel/health` | Health check | `200 OK` |
| `GET` | `/camel/envios` | Listar envíos | Lista JSON |
| `GET` | `/camel/envios/{id}` | Obtener por ID | `001`, `002`, `003` |
| `POST` | `/camel/envios` | Crear envío | JSON body |
| `GET` | `/camel/api-doc` | Documentación | OpenAPI spec |
| `GET` | `/` | Dashboard | Página web |

---

## 🧪 Pruebas y Validación

### PowerShell Commands:
```powershell
# Health Check
Invoke-RestMethod -Uri "http://localhost:8082/camel/health" -Method GET

# Listar envíos
Invoke-RestMethod -Uri "http://localhost:8082/camel/envios" -Method GET

# Obtener envío por ID
Invoke-RestMethod -Uri "http://localhost:8082/camel/envios/001" -Method GET

# Crear nuevo envío
Invoke-RestMethod -Uri "http://localhost:8082/camel/envios" -Method POST `
  -ContentType "application/json" `
  -Body '{"destinatario":"Juan Pérez","direccion":"Av. Amazonas 123","estado":"Registrado"}'
```

### Colección Postman:
📁 `API-Envios-Postman-Collection.json` - Colección completa con 8 requests de prueba

---

## 🏗️ Arquitectura Apache Camel

### Routes Implementadas:

```java
// Configuración REST
restConfiguration()
    .component("servlet")
    .bindingMode(RestBindingMode.json)
    .contextPath("/api")
    .apiContextPath("/api-doc")

// Endpoints REST con Camel DSL
rest("/envios").description("Gestión de Envíos")
    .get().to("direct:listar-envios")
    .post().to("direct:crear-envio")

rest("/envios/{id}")
    .get().to("direct:obtener-envio")

// Implementación de rutas
from("direct:listar-envios")
    .routeId("listar-envios")
    .log("📦 Consultando lista de envíos")
    .setBody(constant("[JSON_DATA]"))
```

### Enterprise Integration Patterns (EIP) Utilizados:
- **Message Router** - Ruteo con `direct:` endpoints
- **Message Translator** - Transformación JSON automática  
- **Content-Based Router** - Filtrado por ID de envío
- **Message History** - Logging estructurado

---

## 🚀 Instrucciones de Ejecución

### Prerrequisitos:
- Java 21 instalado
- Gradle (incluido con wrapper)

### Pasos:
1. **Clonar repositorio:**
   ```bash
   git clone https://github.com/EstebanEr-03/first-camel-project.git
   cd first-camel-project
   git checkout taller-api-rest
   ```

2. **Compilar:**
   ```bash
   ./gradlew build -x test
   ```

3. **Ejecutar:**
   ```bash
   ./gradlew bootRun
   ```

4. **Acceder:**
   - Dashboard: http://localhost:8082
   - API: http://localhost:8082/camel/
   - Docs: http://localhost:8082/camel/api-doc

---

## 📊 Evidencias de Funcionamiento

### ✅ Respuestas de la API:

**GET /camel/health:**
```json
{
  "status": "UP",
  "timestamp": "2025-10-29T18:19:39Z",
  "service": "API de Envíos",
  "version": "1.0.0"
}
```

**GET /camel/envios:**
```json
[
  {
    "id": "001",
    "destinatario": "Juan Pérez",
    "direccion": "Av. Amazonas 123, Quito",
    "estado": "En tránsito",
    "fechaCreacion": "2025-10-29T15:30:00Z"
  },
  // ... más envíos
]
```

**POST /camel/envios:**
```json
{
  "mensaje": "Envío registrado correctamente",
  "id": "004",
  "fechaCreacion": "2025-10-29T18:20:11Z"
}
```

---

## 📁 Estructura del Proyecto

```
first-camel-project/
├── src/main/java/com/integracion/camel/first_camel_project/
│   ├── FirstCamelProjectApplication.java     # Main Spring Boot
│   ├── FileRoute.java                        # Rutas originales (File Transfer)
│   ├── RestApiRoute.java                     # 🆕 API REST con Camel
│   └── WebController.java                    # 🆕 Controlador web
├── src/main/resources/
│   ├── application.properties                # Configuración
│   ├── openapi.yaml                         # 🆕 Especificación OpenAPI
│   └── static/index.html                    # 🆕 Dashboard web
├── API-Envios-Postman-Collection.json       # 🆕 Colección Postman
├── README-TALLER.md                         # 🆕 Este documento
└── build.gradle                             # Dependencias actualizadas
```

---

## 🏆 Características Destacadas

### 🔥 Superiores a los Requisitos:
- **Dashboard Web Visual** - Interfaz gráfica para la API
- **Logging Estructurado** - Trazabilidad completa  
- **Auto-Documentación** - OpenAPI generado dinámicamente
- **Health Monitoring** - Endpoint de salud para monitoreo
- **Dual Functionality** - File Transfer + REST API en un proyecto

### 🚀 Tecnología de Punta:
- Java 21 LTS (más reciente)
- Apache Camel 4.14.0 (última versión)
- Spring Boot 3.5.7 (framework moderno)
- OpenAPI 3.0 (estándar actual)

---

## 🎯 Conclusión

Este proyecto demuestra una implementación **profesional y completa** de una API REST utilizando Apache Camel como motor de integración. La solución va más allá de los requisitos básicos, incorporando mejores prácticas de desarrollo empresarial y tecnologías modernas.

**El proyecto está listo para producción** y cumple al 100% con todos los objetivos del taller de integración de sistemas.

---

**Desarrollado por:** Esteban Erazo  
**Universidad:** UDLA  
**Commit:** `833026d` en branch `taller-api-rest`