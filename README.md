# 🐪 Apache Camel - File Transfer Integration Project

## Descripción
Proyecto de integración de sistemas utilizando **Apache Camel** con **Spring Boot** que implementa el patrón **File Transfer** para procesar archivos CSV con transformaciones automáticas.

## 🚀 Tecnologías Utilizadas
- **Java 21** (LTS)
- **Spring Boot 3.5.7**
- **Apache Camel 4.14.0**
- **Gradle 8.14.3**
- **Enterprise Integration Patterns (EIP)**

## 📋 Características Principales

### ✨ Funcionalidades Implementadas
- 📁 **Procesamiento automático** de archivos CSV
- 🔄 **Transformación de contenido** a mayúsculas
- 📊 **Filtrado por tipo de archivo** (.csv)
- 💾 **Archivado con timestamp** para auditoría
- 📝 **Logging detallado** con fecha y hora
- 👁️ **Monitoreo de archivos** de log
- 🛡️ **Preservación de archivos originales**

### 🏗️ Arquitectura
```
Input Folder → [Filter CSV] → [Transform] → Output Folder
                                      ↓
                              Archived Folder (with timestamp)
```

## 📁 Estructura del Proyecto
```
first-camel-project/
├── src/main/java/
│   └── com/integracion/camel/first_camel_project/
│       ├── FirstCamelProjectApplication.java
│       └── FileRoute.java                     # 🎯 Flujo principal Camel
├── src/main/resources/
│   └── application.properties                 # ⚙️ Configuraciones
├── input/                                     # 📥 Archivos de entrada
├── output/                                    # 📤 Archivos procesados  
├── archived/                                  # 📚 Archivos archivados
├── logs/                                      # 📋 Logs del sistema
└── build.gradle                               # 🔧 Configuración del proyecto
```

## 🚀 Cómo Ejecutar

### Prerequisitos
- Java 21 (LTS)
- Gradle 8.x

### Pasos de Ejecución
1. **Clonar el repositorio** (si aplica)
2. **Compilar el proyecto:**
   ```bash
   ./gradlew build
   ```
3. **Ejecutar la aplicación:**
   ```bash
   ./gradlew bootRun
   ```

### 📥 Datos de Prueba
Coloca archivos CSV en la carpeta `input/` para procesamiento automático.

Ejemplo (`ventas.csv`):
```csv
id,producto,cantidad,precio
1,Monitor,2,150
2,Teclado,5,25
3,Mouse,3,15
```

## 🔧 Configuración

### Java Version
El proyecto está configurado para usar **Java 21**:
```gradle
java {
    toolchain { languageVersion = JavaLanguageVersion.of(21) }
}
```

### Dependencies
- Spring Boot Starter Web
- Spring Boot Actuator  
- Camel Spring Boot Starter
- Camel File Component
- Camel Log Component

## 📊 Flujos de Integración

### 1. File Transfer Route
- **Origen:** `file:input?noop=true&delay=5000`
- **Filtro:** Solo archivos `.csv`
- **Transformación:** Convertir a mayúsculas
- **Destinos:** 
  - `output/` (archivos procesados)
  - `archived/` (con timestamp)

### 2. Log Monitor Route  
- **Origen:** `file:logs?noop=true&delay=10000`
- **Filtro:** Solo archivos `.log`
- **Acción:** Logging de monitoreo

## 📝 Logs de Ejemplo
```
2025-10-25 09:30:49 - Procesando archivo: ventas.csv - Fecha: 2025-10-25 09:30:49
2025-10-25 09:30:49 - Archivo CSV válido: ventas.csv  
2025-10-25 09:30:49 - Contenido transformado a mayúsculas
2025-10-25 09:30:49 - Archivo copiado a output: ventas.csv
2025-10-25 09:30:50 - Archivo archivado con timestamp: ventas-20251025-093050.csv
```

## 🎯 Casos de Uso
- Integración de sistemas legacy
- Procesamiento batch de datos
- Intercambio B2B de archivos
- Transformación automática de formatos
- Sistemas de auditoría y archivado

## 📚 Documentación Adicional
- `ENTREGABLES_TALLER_CAMEL.md` - Documento completo de entregables
- `INFORME_LABORATORIO.md` - Informe técnico detallado

## 👨‍💻 Desarrollo
Este proyecto fue desarrollado como parte del taller de **Integración de Sistemas** utilizando **Enterprise Integration Patterns** con **Apache Camel**.

---
*Proyecto educativo - Integración de Sistemas con Apache Camel y Java 21*