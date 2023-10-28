package com.example.demo.service;

import com.example.demo.DTOs.ViewOrderDto;
import com.example.demo.models.Invoice;
import com.example.demo.models.InvoiceItem;
import com.example.demo.models.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final IInvoiceService invoiceService;

    public OrderService(InvoiceService invoiceService, OrderRepository orderRepository) {
        this.invoiceService = invoiceService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(List<InvoiceItem> items) {
        List<Invoice> invoiceList = new ArrayList<>();
        sortByRatio(items);

        int totalQuantity = cumulativeQuantity(items);
        int unchangedIterations = 0;
        int maxTries = 5;

        while (totalQuantity > 0) {
            Invoice invoice = invoiceService.createInvoice(items);
            int addedQuantity = invoice.getProducts().stream().mapToInt(InvoiceItem::getQuantity).sum();
            totalQuantity = cumulativeQuantity(items);

            if (addedQuantity > 0) {
                unchangedIterations = 0;
            } else {
                unchangedIterations++;
                if (unchangedIterations >= maxTries) {
                    throw new RuntimeException("Error while creating order");
                }
            }

            invoiceList.add(invoice);
        }

        Order order = new Order(invoiceList);
        orderRepository.createOrder(order);

        return order;
    }

    public List<ViewOrderDto> list() {
        List<Order> orders = orderRepository.getAllOrders();

        return orders.stream().map(o -> new ViewOrderDto(
                o.getId(),
                o.getSubTotal(),
                o.getTotal(),
                o.getVat(),
                o.getInvoices().size()
        )).collect(Collectors.toList());
    }

    public Order getOrderDetails(String id) {
        return orderRepository.getOrderDetails(id);
    }

    public void deleteById(String id) {
        orderRepository.deleteOrder(id);
    }

    private void sortByRatio(List<InvoiceItem> items) {
        items.sort(Comparator.comparing(item ->
                item.getProduct().getTotalPrice()
                        .divide(new BigDecimal(item.getQuantity()), 6, RoundingMode.HALF_DOWN))
        );
        Collections.reverse(items);
    }

    private int cumulativeQuantity(List<InvoiceItem> items) {
        return items.stream().map(InvoiceItem::getQuantity).reduce(0, Integer::sum);
    }
}