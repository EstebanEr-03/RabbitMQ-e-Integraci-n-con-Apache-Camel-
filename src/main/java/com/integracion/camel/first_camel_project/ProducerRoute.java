package com.integracion.camel.first_camel_project;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@EnableScheduling
public class ProducerRoute {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String QUEUE_NAME = "test.camel.queue";

    @Bean
    public Queue testQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String message = "Mensaje generado en " + timestamp;
        
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);
        System.out.println(timestamp + " - Enviando: " + message);
    }
}
