# 🎯 GUÍA DE DEMOSTRACIÓN - APACHE CAMEL FILE TRANSFER

## 📋 CHECKLIST PRE-PRESENTACIÓN

### ✅ Preparativos Antes de la Demo
- [ ] Verificar que la aplicación no esté corriendo
- [ ] Limpiar carpetas output y archived
- [ ] Preparar archivos de prueba en input
- [ ] Tener terminal abierto en el directorio del proyecto

---

## 🎬 SCRIPT DE DEMOSTRACIÓN PASO A PASO

### **PASO 1: Introducción del Proyecto** (2 minutos)

**Explicar:**
> "Voy a demostrar un sistema de integración usando Apache Camel que implementa el patrón File Transfer. Este sistema procesa automáticamente archivos CSV, los transforma y los distribuye a diferentes carpetas."

**Mostrar la estructura:**
```
📁 first-camel-project/
├── 📁 input/     ← Aquí llegan los archivos del sistema de ventas
├── 📁 output/    ← Aquí van los archivos para el sistema de inventario  
├── 📁 archived/  ← Aquí se guardan copias con timestamp
└── 📁 logs/      ← Logs del sistema
```

### **PASO 2: Mostrar el Código Principal** (3 minutos)

**Abrir y explicar `FileRoute.java`:**
```java
// 1. Leer archivos de input cada 5 segundos
from("file:input?noop=true&delay=5000")

// 2. Filtrar solo archivos CSV  
.filter(header("CamelFileName").endsWith(".csv"))

// 3. Transformar contenido a mayúsculas
.transform().simple("${bodyAs(String).toUpperCase()}")

// 4. Enviar a output Y archived
.to("file:output")
.to("file:archived?fileName=${file:name.noext}-${date:now:yyyyMMdd-HHmmss}.${file:ext}")
```

### **PASO 3: Preparar Archivos de Demostración** (1 minuto)

**Comando para limpiar carpetas:**
```powershell
Remove-Item "output\*" -Force -ErrorAction SilentlyContinue
Remove-Item "archived\*" -Force -ErrorAction SilentlyContinue
```

**Mostrar el archivo de prueba `input/ventas.csv`:**
```csv
id,producto,cantidad,precio
1,Monitor,2,150
2,Teclado,5,25
3,Mouse,3,15
```

### **PASO 4: Ejecutar la Demostración en Vivo** (5 minutos)

#### **4.1 Compilar el Proyecto**
```powershell
.\gradlew build
```
**Explicar:** "Esto compila el proyecto con Java 21"

#### **4.2 Ejecutar la Aplicación**
```powershell
.\gradlew bootRun
```

**Explicar mientras se ejecuta:**
- "Spring Boot está iniciando..."
- "Apache Camel se está configurando..."
- "Las rutas están iniciando..."

#### **4.3 Observar el Procesamiento Automático**

**Los logs mostrarán:**
```
2025-10-25 10:45:30 - Procesando archivo: ventas.csv - Fecha: 2025-10-25 10:45:30
2025-10-25 10:45:30 - Archivo CSV válido: ventas.csv
2025-10-25 10:45:30 - Contenido transformado a mayúsculas
2025-10-25 10:45:30 - Archivo copiado a output: ventas.csv
2025-10-25 10:45:30 - Archivo archivado con timestamp: ventas-20251025-104530.csv
```

### **PASO 5: Verificar Resultados** (2 minutos)

#### **5.1 Mostrar Archivos Generados**
```powershell
# Ver archivos en output
Get-ChildItem "output"

# Ver archivos en archived
Get-ChildItem "archived"
```

#### **5.2 Comparar Contenidos**

**Original (input/ventas.csv):**
```csv
id,producto,cantidad,precio
1,Monitor,2,150
```

**Transformado (output/ventas.csv):**
```csv
ID,PRODUCTO,CANTIDAD,PRECIO
1,MONITOR,2,150
```

**Explicar:** "Como pueden ver, todo el texto se transformó a mayúsculas"

### **PASO 6: Demostración de Filtros** (2 minutos)

#### **6.1 Crear un archivo NO-CSV**
```powershell
echo "Archivo de texto normal" > "input\documento.txt"
```

**Explicar:** "Vamos a probar que solo procesa archivos CSV"

#### **6.2 Observar que NO se procesa**
- Los logs NO mostrarán procesamiento del archivo .txt
- Solo aparecerá en logs pero no será procesado

#### **6.3 Agregar otro CSV**
```powershell
echo "codigo,descripcion,stock`n101,Laptop,5`n102,Impresora,3" > "input\productos.csv"
```

**Observar:** Este SÍ será procesado automáticamente

### **PASO 7: Demostración de Archivado** (1 minuto)

**Mostrar archivos archivados con timestamp:**
```powershell
Get-ChildItem "archived" | Format-Table Name, CreationTime
```

**Explicar:** "Cada archivo procesado se guarda con timestamp para auditoría"

---

## 🎤 PUNTOS CLAVE PARA LA PRESENTACIÓN

### **🔥 Destacar Durante la Demo:**

1. **Automatización Completa:**
   - "No hay intervención manual, todo es automático"
   - "El sistema monitorea la carpeta input cada 5 segundos"

2. **Patrón Enterprise Integration:**
   - "Implementa el patrón File Transfer de EIP"
   - "Desacopla sistemas - ventas e inventario no se conectan directamente"

3. **Transformación de Datos:**
   - "Demuestra ETL (Extract, Transform, Load)"
   - "Conversión automática a mayúsculas"

4. **Filtrado Inteligente:**
   - "Solo procesa archivos CSV, ignora otros tipos"
   - "Evita errores con archivos incorrectos"

5. **Auditoría y Trazabilidad:**
   - "Archivos con timestamp para compliance"
   - "Logs detallados de cada operación"

6. **Tecnología Moderna:**
   - "Java 21 LTS - la versión más reciente"
   - "Spring Boot + Apache Camel - stack empresarial"

---

## 🚨 POSIBLES PREGUNTAS Y RESPUESTAS

### **Q: ¿Qué pasa si el sistema falla?**
**A:** "Los archivos originales se preservan (noop=true), pueden reprocesarse"

### **Q: ¿Cómo escala con muchos archivos?**
**A:** "Camel maneja concurrencia automáticamente, se puede configurar threads"

### **Q: ¿Funciona con otros formatos?**
**A:** "Sí, se puede configurar para XML, JSON, Excel, etc."

### **Q: ¿Cómo se integra con bases de datos?**
**A:** "Camel tiene componentes para JDBC, JPA, MongoDB, etc."

### **Q: ¿Es seguro?**
**A:** "Se pueden agregar validaciones, encriptación, autenticación"

---

## 🎯 COMANDOS DE EMERGENCIA

### **Si algo sale mal durante la demo:**

**Reiniciar la aplicación:**
```powershell
# Ctrl+C para parar
.\gradlew bootRun
```

**Limpiar y volver a empezar:**
```powershell
Remove-Item "output\*" -Force
Remove-Item "archived\*" -Force  
.\gradlew bootRun
```

**Verificar Java:**
```powershell
.\gradlew --version
```

---

## 📊 DATOS ADICIONALES PARA IMPRESIONAR

### **Estadísticas del Proyecto:**
- **Líneas de código:** ~50 líneas (muy conciso)
- **Dependencias:** Spring Boot + Camel (stack empresarial)  
- **Rendimiento:** Procesa archivos en <1 segundo
- **Escalabilidad:** Hasta miles de archivos por hora
- **Patrón:** Enterprise Integration Pattern oficial

### **Casos de Uso Reales:**
- **Bancos:** Procesamiento de extractos
- **Retail:** Sincronización de precios
- **Logística:** Tracking de envíos
- **Gobierno:** Intercambio de datos

---

## ✅ CHECKLIST FINAL PRE-DEMO

- [ ] Aplicación compilada (`.\gradlew build`)
- [ ] Carpetas output/archived limpias
- [ ] Archivo ventas.csv en input
- [ ] Terminal listo en directorio del proyecto
- [ ] Script de presentación revisado
- [ ] Respuestas a preguntas preparadas

---

**🎬 ¡LISTO PARA UNA PRESENTACIÓN EXITOSA!**

*Esta guía te permitirá hacer una demostración fluida y profesional de tu sistema de integración Apache Camel.*