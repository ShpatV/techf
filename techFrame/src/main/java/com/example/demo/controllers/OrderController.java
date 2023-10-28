package com.example.demo.controllers;


import com.example.demo.DTOs.ViewOrderDto;
import com.example.demo.models.InvoiceItem;
import com.example.demo.models.Order;
import com.example.demo.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public ResponseEntity<List<ViewOrderDto>> getAllOrders() {
        List<ViewOrderDto> orders = orderService.list();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<Order> getOrderDetails(@PathVariable String id) {
        Order order = orderService.getOrderDetails(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody List<InvoiceItem> items) {
        Order order = orderService.createOrder(items);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable String id) {
        orderService.deleteById(id);
        return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
    }
}