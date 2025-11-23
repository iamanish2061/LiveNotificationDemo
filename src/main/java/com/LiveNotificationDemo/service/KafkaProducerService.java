package com.LiveNotificationDemo.service;

import com.LiveNotificationDemo.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendOrderEvent(OrderEvent event) {
        kafkaTemplate.send("order-events", event);
    }

    public void sendNotificationEvent(OrderEvent event) {
        kafkaTemplate.send("notification-events", event);
    }
}