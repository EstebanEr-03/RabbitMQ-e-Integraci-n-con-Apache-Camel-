package com.integracion.camel.first_camel_project;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ConsumerRoute {

    @RabbitListener(queues = "test.camel.queue")
    public void receiveMessage(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(timestamp + " - Mensaje recibido: " + message);
    }
}
