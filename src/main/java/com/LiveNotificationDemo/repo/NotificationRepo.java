package com.LiveNotificationDemo.repo;

import com.LiveNotificationDemo.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Long> {

    List<Notification> findByUsernameAndIsReadFalse(String username);

    List<Notification> findByUsernameOrderByCreatedAtDesc(String username);

}
