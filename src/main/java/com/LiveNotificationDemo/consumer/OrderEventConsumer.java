package com.LiveNotificationDemo.consumer;

import com.LiveNotificationDemo.dto.OrderEvent;
import com.LiveNotificationDemo.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void consumeOrderEvent(OrderEvent event) {
        log.info("New order event received from Kafka → pushing to all admins: {}", event);

        // Instantly push to all connected admins
        notificationService.sendToAllAdmins(event);
    }
}