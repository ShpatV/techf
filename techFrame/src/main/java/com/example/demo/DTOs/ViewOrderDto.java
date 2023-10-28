package com.example.demo.DTOs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ViewOrderDto {
    private String id;
    private BigDecimal subTotal;
    private BigDecimal totalAmount;
    private BigDecimal vat;
    private int numberOfInvoices;

}
