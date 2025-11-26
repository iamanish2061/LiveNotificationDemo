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
public class NotificationEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "notification-events", groupId = "notification-group")
    public void consume(OrderEvent event) {
        notificationService.saveAndSendNotification(event.customerUsername(), event);
    }

}