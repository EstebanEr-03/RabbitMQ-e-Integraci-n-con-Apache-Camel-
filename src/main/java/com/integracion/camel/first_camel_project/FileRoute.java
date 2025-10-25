package com.integracion.camel.first_camel_project;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRoute extends RouteBuilder {
    @Override
    public void configure() {

        // Paso 3 y 5: Flujo con transformación - Lee archivos, transforma a mayúsculas y copia a output
        from("file:input?noop=true&delay=5000")
            .routeId("file-transfer-with-transformation")
            .log("Procesando archivo: ${file:name} - Fecha: ${date:now:yyyy-MM-dd HH:mm:ss}")
            
            // Paso 6: Filtro para solo procesar archivos CSV
            .filter(header("CamelFileName").endsWith(".csv"))
            .log("Archivo CSV válido: ${file:name}")
            
            // Paso 5: Transformar contenido a mayúsculas
            .transform().simple("${bodyAs(String).toUpperCase()}")
            .log("Contenido transformado a mayúsculas")
            
            // Enviar a carpeta output
            .to("file:output")
            .log("Archivo copiado a output: ${file:name}")
            
            // Paso 6: Ruta extra - Archivar con timestamp
            .to("file:archived?fileName=${file:name.noext}-${date:now:yyyyMMdd-HHmmss}.${file:ext}")
            .log("Archivo archivado con timestamp: ${file:name.noext}-${date:now:yyyyMMdd-HHmmss}.${file:ext}");
            
        // Paso 6: Ruta adicional para monitorear logs
        from("file:logs?noop=true&delay=10000")
            .routeId("log-monitor")
            .log("Monitoreando archivos de log: ${file:name}")
            .filter(header("CamelFileName").endsWith(".log"))
            .log("Archivo de log procesado: ${file:name}");
    }
}
