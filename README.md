# 🐰 RabbitMQ e Integración con Spring Boot

## 📌 Objetivo

**Aplicar el patrón de mensajería asincrónica** para demostrar el desacoplamiento entre productores y consumidores, configurando un broker de mensajería RabbitMQ y conectando productores y consumidores de mensajes usando Spring AMQP.

## 🎯 Patrón de Integración Implementado

### **Message Queue Pattern (Mensajería Asíncrona)**

Este proyecto implementa el patrón **Message Queue** que permite:

- ✅ **Desacoplamiento**: El productor y consumidor no se conocen entre sí ni necesitan estar activos simultáneamente
- ✅ **Comunicación Asíncrona**: Los mensajes se envían sin esperar respuesta inmediata
- ✅ **Persistencia de Mensajes**: RabbitMQ almacena los mensajes en cola hasta que sean consumidos
- ✅ **Escalabilidad**: Múltiples consumidores pueden procesar mensajes en paralelo
- ✅ **Confiabilidad**: Si el consumidor está caído, los mensajes se acumulan en la cola

### Flujo de Mensajería

```
┌─────────────┐         ┌──────────────┐         ┌──────────────┐
│  Producer   │ ───────>│   RabbitMQ   │ ───────>│   Consumer   │
│   Route     │ Publish │    Queue     │ Consume │    Route     │
└─────────────┘         └──────────────┘         └──────────────┘
     (cada 5s)         test.camel.queue         (listener activo)
```

## 🚀 Tecnologías Utilizadas

- **Java 21** - Lenguaje de programación
- **Spring Boot 3.5.7** - Framework backend
- **Spring AMQP** - Cliente de RabbitMQ para Java
- **RabbitMQ 3** - Message broker
- **Docker** - Contenedor para RabbitMQ
- **Gradle 8.x** - Gestión de dependencias

## 📋 Requisitos Previos

- **Java JDK 21** o superior
- **Docker Desktop** (para ejecutar RabbitMQ)
- **Gradle 8.x** (incluido en wrapper)

## 🔧 Configuración e Instalación

### 1. Clonar el Repositorio

```powershell
git clone https://github.com/EstebanEr-03/RabbitMQ-e-Integraci-n-con-Apache-Camel-.git
cd RabbitMQ-e-Integraci-n-con-Apache-Camel-
```

### 2. Iniciar RabbitMQ con Docker

```powershell
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 `
  -e RABBITMQ_DEFAULT_USER=admin `
  -e RABBITMQ_DEFAULT_PASS=admin123 `
  rabbitmq:3-management
```

**Puertos:**
- `5672` - Puerto AMQP para conexión de aplicaciones
- `15672` - Puerto web para Management UI

### 3. Compilar el Proyecto

```powershell
.\gradlew.bat clean build -x test
```

### 4. Ejecutar la Aplicación

```powershell
.\gradlew.bat bootRun
```

## 📁 Estructura del Proyecto

```
first-camel-project/
├── src/main/java/
│   └── com/integracion/camel/first_camel_project/
│       ├── FirstCamelProjectApplication.java   # Clase principal Spring Boot
│       ├── ProducerRoute.java                  # 🔵 Productor de mensajes
│       └── ConsumerRoute.java                  # 🟢 Consumidor de mensajes
├── src/main/resources/
│   └── application.properties                  # Configuración RabbitMQ
├── build.gradle                                # Dependencias del proyecto
└── README.md                                   # Este archivo
```

## 🔵 Componente Producer (ProducerRoute.java)

**Responsabilidad:** Generar y enviar mensajes cada 5 segundos a RabbitMQ.

```java
@Component
@EnableScheduling
public class ProducerRoute {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        String message = "Mensaje generado en " + timestamp;
        rabbitTemplate.convertAndSend("test.camel.queue", message);
    }
}
```

**Características:**
- ⏱️ Programación automática cada 5 segundos (`@Scheduled`)
- 📤 Envío de mensajes con timestamp
- 🔗 Desacoplado del consumidor

## 🟢 Componente Consumer (ConsumerRoute.java)

**Responsabilidad:** Escuchar y procesar mensajes de la cola de RabbitMQ.

```java
@Component
public class ConsumerRoute {
    @RabbitListener(queues = "test.camel.queue")
    public void receiveMessage(String message) {
        System.out.println("Mensaje recibido: " + message);
    }
}
```

**Características:**
- 👂 Listener activo (`@RabbitListener`)
- 🔄 Procesamiento automático de mensajes
- 🔗 Desacoplado del productor

## 🎮 Demostración del Desacoplamiento

### Escenario 1: Consumidor Desactivado

1. **Comentar** el método `receiveMessage` en `ConsumerRoute.java`:
```java
// @RabbitListener(queues = "test.camel.queue")
// public void receiveMessage(String message) { ... }
```

2. Reiniciar la aplicación

3. **Resultado:** Los mensajes se acumulan en la cola sin perderse

4. Verificar en RabbitMQ Management UI (http://localhost:15672):
   - Ir a **Queues** → `test.camel.queue`
   - Ver el contador **"Ready"** incrementándose

### Escenario 2: Reactivar Consumidor

1. Descomentar el método `receiveMessage`
2. Reiniciar aplicación
3. **Resultado:** Todos los mensajes acumulados se procesan inmediatamente

**Esto demuestra:**
- ✅ Persistencia de mensajes
- ✅ Desacoplamiento temporal
- ✅ Confiabilidad del broker

## 🌐 Panel de Administración RabbitMQ

Accede al Management UI en: **http://localhost:15672**

**Credenciales:**
- Usuario: `admin`
- Contraseña: `admin123`

**Funcionalidades:**
- Ver colas y mensajes en tiempo real
- Monitorear conexiones activas
- Estadísticas de mensajes enviados/recibidos
- Publicar/consumir mensajes manualmente

## 📊 Salida Esperada

Al ejecutar la aplicación, verás en consola:

```
2025-12-17 20:50:38 - Enviando: Mensaje generado en 2025-12-17 20:50:38
2025-12-17 20:50:38 - Mensaje recibido: Mensaje generado en 2025-12-17 20:50:38
2025-12-17 20:50:43 - Enviando: Mensaje generado en 2025-12-17 20:50:43
2025-12-17 20:50:43 - Mensaje recibido: Mensaje generado en 2025-12-17 20:50:43
```

## 🧪 Pruebas Realizadas

1. ✅ Envío y recepción de mensajes en tiempo real
2. ✅ Acumulación de mensajes con consumidor desactivado
3. ✅ Procesamiento de mensajes acumulados al reactivar consumidor
4. ✅ Persistencia de mensajes durante reinicio de aplicación
5. ✅ Conexión exitosa con RabbitMQ broker

## 📚 Conceptos Clave de Mensajería Asíncrona

### Ventajas

- **Desacoplamiento espacial**: Los componentes no necesitan conocerse mutuamente
- **Desacoplamiento temporal**: No necesitan estar activos simultáneamente
- **Escalabilidad horizontal**: Fácil agregar más consumidores
- **Tolerancia a fallos**: Mensajes no se pierden si un componente falla
- **Balance de carga**: Distribución automática entre múltiples consumidores

### Casos de Uso

- Procesamiento de tareas en segundo plano
- Integración entre microservicios
- Notificaciones asíncronas
- Procesamiento de eventos
- Sistemas de cola de trabajo

## 🛠️ Configuración

Archivo `application.properties`:

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=admin123
```

## 👨‍💻 Autor

**Esteban Enríquez**
- GitHub: [@EstebanEr-03](https://github.com/EstebanEr-03)

## 📄 Licencia

Proyecto educativo - Taller de Integración de Sistemas

---

**Universidad:** UDLA
**Asignatura:** Integración de Sistemas  
**Fecha:** Diciembre 2025
