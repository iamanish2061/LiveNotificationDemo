package com.LiveNotificationDemo.service;

import com.LiveNotificationDemo.dto.OrderEvent;
import com.LiveNotificationDemo.entity.Order;
import com.LiveNotificationDemo.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final KafkaProducerService kafkaProducer;

    public Order placeOrder(String productName, Double price, String customerUsername) {
        Order order = Order.builder()
                .productName(productName)
                .price(price)
                .customerUsername(customerUsername)
                .status(Order.OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        order = orderRepo.save(order);

        // Send event to Kafka
        OrderEvent event = new OrderEvent(
                order.getId(),
                customerUsername,
                productName,
                price,
                order.getStatus(),
                "New order placed!"
        );
        kafkaProducer.sendOrderEvent(event);

        return order;
    }



    public List<Order> fetchAllOrders() {
        return orderRepo.findAll();
    }

    public Order markOutForDelivery(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(Order.OrderStatus.OUT_FOR_DELIVERY);
        order = orderRepo.save(order);

        OrderEvent event = new OrderEvent(
                order.getId(),
                order.getCustomerUsername(),
                order.getProductName(),
                order.getPrice(),
                order.getStatus(),
                "Your order is Out for Delivery!"
        );

        kafkaProducer.sendNotificationEvent(event);
        return order;
    }

    public Order markDelivered(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(Order.OrderStatus.DELIVERED);
        order = orderRepo.save(order);

        OrderEvent event = new OrderEvent(
                order.getId(),
                order.getCustomerUsername(),
                order.getProductName(),
                order.getPrice(),
                order.getStatus(),
                "Your order has been Delivered!"
        );
        kafkaProducer.sendNotificationEvent(event);
        return order;
    }

}
