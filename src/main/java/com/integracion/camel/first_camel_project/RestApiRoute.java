package com.integracion.camel.first_camel_project;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RestApiRoute extends RouteBuilder {
    
    // Contador para generar IDs únicos
    private static final AtomicInteger counter = new AtomicInteger(1);
    
    @Override
    public void configure() {
        
        // Configuración REST
        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true")
            .contextPath("/api")
            .port(8081)
            .apiContextPath("/api-doc")
            .apiProperty("api.title", "API de Envíos")
            .apiProperty("api.version", "1.0.0")
            .apiProperty("api.description", "API REST para gestión de envíos con Apache Camel")
            .apiProperty("cors", "true");

        // Definición de endpoints REST
        rest("/envios").description("Gestión de Envíos")
            .consumes("application/json")
            .produces("application/json")
            
            // GET /envios - Listar todos los envíos
            .get()
                .description("Obtener lista de todos los envíos")
                .responseMessage().code(200).message("Lista de envíos").endResponseMessage()
                .to("direct:listar-envios")
            
            // POST /envios - Crear nuevo envío
            .post()
                .description("Crear un nuevo envío")
                .type(EnvioRequest.class)
                .responseMessage().code(201).message("Envío creado").endResponseMessage()
                .responseMessage().code(400).message("Datos inválidos").endResponseMessage()
                .to("direct:crear-envio");

        // Endpoint para obtener envío por ID
        rest("/envios/{id}").description("Consulta de envío específico")
            .produces("application/json")
            
            // GET /envios/{id} - Obtener envío por ID
            .get()
                .description("Obtener detalles de un envío por ID")
                .responseMessage().code(200).message("Envío encontrado").endResponseMessage()
                .responseMessage().code(404).message("Envío no encontrado").endResponseMessage()
                .to("direct:obtener-envio");

        // Endpoint de salud
        rest("/health").description("Health Check")
            .produces("application/json")
            .get()
                .description("Verificar estado del servicio")
                .responseMessage().code(200).message("Servicio funcionando").endResponseMessage()
                .to("direct:health-check");

        // =====================================================
        // IMPLEMENTACIÓN DE LAS RUTAS
        // =====================================================

        // Ruta: Listar envíos
        from("direct:listar-envios")
            .routeId("listar-envios")
            .log("📦 Consultando lista de envíos")
            .setBody(constant("["
                + "{"
                + "\"id\":\"001\","
                + "\"destinatario\":\"Juan Pérez\","
                + "\"direccion\":\"Av. Amazonas 123, Quito\","
                + "\"estado\":\"En tránsito\","
                + "\"fechaCreacion\":\"2025-10-29T15:30:00Z\""
                + "},"
                + "{"
                + "\"id\":\"002\","
                + "\"destinatario\":\"María López\","
                + "\"direccion\":\"Av. Central 456, Guayaquil\","
                + "\"estado\":\"Entregado\","
                + "\"fechaCreacion\":\"2025-10-29T14:15:00Z\""
                + "},"
                + "{"
                + "\"id\":\"003\","
                + "\"destinatario\":\"Carlos Mendoza\","
                + "\"direccion\":\"Calle 10 de Agosto 789, Cuenca\","
                + "\"estado\":\"Registrado\","
                + "\"fechaCreacion\":\"2025-10-29T16:45:00Z\""
                + "}"
                + "]"))
            .log("✅ Lista de envíos devuelta: ${body}")
            .setHeader("Content-Type", constant("application/json"));

        // Ruta: Crear envío
        from("direct:crear-envio")
            .routeId("crear-envio")
            .log("📥 Nuevo envío recibido: ${body}")
            .process(exchange -> {
                // Generar ID único
                String nuevoId = String.format("%03d", counter.getAndIncrement() + 3);
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z";
                
                // Respuesta simulada
                String respuesta = "{"
                    + "\"mensaje\":\"Envío registrado correctamente\","
                    + "\"id\":\"" + nuevoId + "\","
                    + "\"fechaCreacion\":\"" + timestamp + "\""
                    + "}";
                
                exchange.getIn().setBody(respuesta);
                exchange.getIn().setHeader("Content-Type", "application/json");
            })
            .log("✅ Envío creado con respuesta: ${body}")
            .setHeader("CamelHttpResponseCode", constant(201));

        // Ruta: Obtener envío por ID
        from("direct:obtener-envio")
            .routeId("obtener-envio")
            .log("🔍 Consultando envío con ID: ${header.id}")
            .choice()
                .when(header("id").isEqualTo("001"))
                    .setBody(constant("{"
                        + "\"id\":\"001\","
                        + "\"destinatario\":\"Juan Pérez\","
                        + "\"direccion\":\"Av. Amazonas 123, Quito\","
                        + "\"estado\":\"En tránsito\","
                        + "\"fechaCreacion\":\"2025-10-29T15:30:00Z\""
                        + "}"))
                .when(header("id").isEqualTo("002"))
                    .setBody(constant("{"
                        + "\"id\":\"002\","
                        + "\"destinatario\":\"María López\","
                        + "\"direccion\":\"Av. Central 456, Guayaquil\","
                        + "\"estado\":\"Entregado\","
                        + "\"fechaCreacion\":\"2025-10-29T14:15:00Z\""
                        + "}"))
                .when(header("id").isEqualTo("003"))
                    .setBody(constant("{"
                        + "\"id\":\"003\","
                        + "\"destinatario\":\"Carlos Mendoza\","
                        + "\"direccion\":\"Calle 10 de Agosto 789, Cuenca\","
                        + "\"estado\":\"Registrado\","
                        + "\"fechaCreacion\":\"2025-10-29T16:45:00Z\""
                        + "}"))
                .otherwise()
                    .setHeader("CamelHttpResponseCode", constant(404))
                    .setBody(constant("{\"error\":\"Envío no encontrado\",\"id\":\"${header.id}\"}"))
            .end()
            .log("✅ Respuesta para envío ${header.id}: ${body}")
            .setHeader("Content-Type", constant("application/json"));

        // Ruta: Health Check
        from("direct:health-check")
            .routeId("health-check")
            .log("💚 Health check solicitado")
            .process(exchange -> {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z";
                String healthResponse = "{"
                    + "\"status\":\"UP\","
                    + "\"timestamp\":\"" + timestamp + "\","
                    + "\"service\":\"API de Envíos\","
                    + "\"version\":\"1.0.0\""
                    + "}";
                exchange.getIn().setBody(healthResponse);
                exchange.getIn().setHeader("Content-Type", "application/json");
            })
            .log("✅ Health check: ${body}");
    }

    // Clase para el request body
    public static class EnvioRequest {
        private String destinatario;
        private String direccion;
        private String estado = "Registrado";

        // Getters y setters
        public String getDestinatario() { return destinatario; }
        public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
        
        public String getDireccion() { return direccion; }
        public void setDireccion(String direccion) { this.direccion = direccion; }
        
        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }
    }
}