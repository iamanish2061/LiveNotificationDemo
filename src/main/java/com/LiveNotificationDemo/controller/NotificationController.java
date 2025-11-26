package com.LiveNotificationDemo.controller;

import com.LiveNotificationDemo.entity.Notification;
import com.LiveNotificationDemo.repo.NotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepo notificationRepo;

    @GetMapping("/unread")
    public long getUnreadCount(@RequestParam String username) {
        return notificationRepo.findByUsernameAndIsReadFalse(username).size();
    }

    @GetMapping("/all")
    public List<Notification> getAll(@RequestParam String username) {
        return notificationRepo.findByUsernameOrderByCreatedAtDesc(username);
    }

    @PostMapping("/read/{id}")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        Notification n = notificationRepo.findById(id).orElseThrow();
        n.setRead(true);
        notificationRepo.save(n);
        return ResponseEntity.ok().build();
    }
}
