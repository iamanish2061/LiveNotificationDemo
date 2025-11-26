package com.LiveNotificationDemo.service;

import com.LiveNotificationDemo.dto.OrderEvent;
import com.LiveNotificationDemo.entity.Notification;
import com.LiveNotificationDemo.entity.Users;
import com.LiveNotificationDemo.repo.NotificationRepo;
import com.LiveNotificationDemo.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepo notificationRepo;
    private final UserRepo userRepo;

    // Send to ALL admins (we'll use a common channel)
    public void sendToAllAdmins(OrderEvent event) {
        messagingTemplate.convertAndSend("/topic/admin/orders", event);
    }

    public void saveAndSendNotification(String username, OrderEvent event){
        Notification notification = Notification.builder()
                .username(username)
                .title("Order # " +event.orderId())
                .message(event.message())
                .orderId(String.valueOf(event.orderId()))
                .isRead(false)
                .build();
        notificationRepo.save(notification);

        messagingTemplate.convertAndSend("/topic/notifications/"+username, event);
    }

    public void saveAndSendToAllAdmins(OrderEvent event) {
        List<Users> admins = userRepo.findByRole(Users.Role.ROLE_ADMIN);

        for(Users admin :admins){
            saveAndSendNotification(admin.getUsername(), event);
        }

    }
}