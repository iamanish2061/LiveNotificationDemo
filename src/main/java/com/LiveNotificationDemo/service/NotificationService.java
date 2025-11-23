package com.LiveNotificationDemo.service;

import com.LiveNotificationDemo.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    // Send to a specific customer only
    public void sendPrivateNotification(String username, OrderEvent event) {
        messagingTemplate.convertAndSend("/topic/notifications/" + username, event);
    }

    // Send to ALL admins (we'll use a common channel)
    public void sendToAllAdmins(OrderEvent event) {
        messagingTemplate.convertAndSend("/topic/admin/orders", event);
    }
}