package com.LiveNotificationDemo.controller;

import com.LiveNotificationDemo.entity.Order;
import com.LiveNotificationDemo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.fetchAllOrders();
    }

    @PostMapping("/order/{id}/out-for-delivery")
    public Order outForDelivery(@PathVariable Long id) {
        return orderService.markOutForDelivery(id);
    }

    @PostMapping("/order/{id}/delivered")
    public Order delivered(@PathVariable Long id) {
        return orderService.markDelivered(id);
    }

}
