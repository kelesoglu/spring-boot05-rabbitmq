package com.example.springboot05rabbitmq.producer;

import com.example.springboot05rabbitmq.model.Notification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

@Service
public class NotificationProducer {
    @Value("${sb.rabbit.routing.name}")
    private String routingName;

    @Value("${sb.rabbit.exchange.name}")
    private String exchangeName;

    public NotificationProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate =rabbitTemplate;
    }

    @PostConstruct
    public void init(){
        Notification notification=new Notification();
        notification.setNotificationId(UUID.randomUUID().toString());
        notification.setCreatedAt(new Date());
        notification.setMessage("Techcareer bootcamp eğitmine hoşgeldiniz.");
        notification.setSeen(Boolean.FALSE);

        sendToQueue(notification);
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private void sendToQueue(Notification notification) {
        System.out.println("Notification sent ID " +notification.getNotificationId());
        rabbitTemplate.convertAndSend(exchangeName,routingName,notification);
    }
}
