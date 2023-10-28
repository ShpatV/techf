package com.example.demo.models;

import com.example.demo.IdGenerator;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Order {
    private String id;
    private List<Invoice> invoices;
    private BigDecimal subTotal;
    private BigDecimal vat;
    private BigDecimal total;

    private static final String PREFIX = "ORD";
    private static final int ID_LENGTH = 2;
    private static int counter = 1;

    public Order() {
        this(new ArrayList<>());
    }

    public Order(List<Invoice> invoices) {
        this.id = IdGenerator.generateId(PREFIX, ID_LENGTH, counter++);
        this.invoices = invoices;
        calculateTotals();
    }

    private void calculateTotals() {
        total = calculateTotal();
        subTotal = calculateSubTotal();
        vat = total.subtract(subTotal);
    }

    private BigDecimal calculateTotal() {
        BigDecimal total = new BigDecimal(0);
        for (Invoice invoice : invoices) {
            total = total.add(invoice.calculateTotalAmount());
        }

        return total;
    }

    private BigDecimal calculateSubTotal() {
        BigDecimal subTotal = new BigDecimal(0);
        for (Invoice invoice : invoices) {
            subTotal = subTotal.add(invoice.calculateSubTotal());
        }

        return subTotal;
    }
}