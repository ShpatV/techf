package com.example.demo.service;

import com.example.demo.DTOs.ViewOrderDto;
import com.example.demo.models.InvoiceItem;
import com.example.demo.models.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(List<InvoiceItem> items);

    List<ViewOrderDto> list();

    Order getOrderDetails(String id);

    void deleteById(String id);
}
