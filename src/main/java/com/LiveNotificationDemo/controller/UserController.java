package com.LiveNotificationDemo.controller;

import com.LiveNotificationDemo.entity.Order;
import com.LiveNotificationDemo.entity.UserPrincipal;
import com.LiveNotificationDemo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final OrderService orderService;

    @PostMapping("/order")
    public Order placeOrder(@RequestBody OrderRequest request,
                            @AuthenticationPrincipal UserPrincipal user) {

        return orderService.placeOrder(
                request.productName(), request.price(), user.getUsername()
        );

    }
}

// Tiny request body
record OrderRequest(String productName, Double price) {}