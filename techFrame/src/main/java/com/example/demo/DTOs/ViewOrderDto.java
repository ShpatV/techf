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

    public ViewOrderDto(String id, BigDecimal subTotal, BigDecimal totalAmount, BigDecimal vat, int numberOfInvoices) {
        this.id = id;
        this.subTotal = subTotal;
        this.totalAmount = totalAmount;
        this.vat = vat;
        this.numberOfInvoices = numberOfInvoices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public int getNumberOfInvoices() {
        return numberOfInvoices;
    }

    public void setNumberOfInvoices(int numberOfInvoices) {
        this.numberOfInvoices = numberOfInvoices;
    }
}

