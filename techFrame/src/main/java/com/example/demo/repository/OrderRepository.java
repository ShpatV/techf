package com.example.demo.repository;

import com.example.demo.models.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private final List<Order> orders = new ArrayList<>();

    public void createOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getAllOrders() {
        return orders;
    }

    public Order getOrderDetails(String id) {
        return orders.stream().filter(order -> order.getId().equals(id)).findFirst().orElse(null);
    }

    public void deleteOrder(String id) {
        orders.removeIf(order -> order.getId().equals(id));
    }
}