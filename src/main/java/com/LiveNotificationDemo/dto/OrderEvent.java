package com.LiveNotificationDemo.dto;

import com.LiveNotificationDemo.entity.Order.OrderStatus;

public record OrderEvent(
        Long orderId,
        String customerUsername,
        String productName,
        Double price,
        OrderStatus status,
        String message
) {}