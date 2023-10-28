package com.example.demo.models;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class InvoiceItem {
    private Product product;
    private Integer quantity;
    private BigDecimal subTotal;
    private BigDecimal vat;

    public InvoiceItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        subTotal = product.getDiscountPrice().multiply(new BigDecimal(quantity));
        vat = subTotal.multiply(product.getVat().divide(new BigDecimal(100), 4, RoundingMode.HALF_DOWN))
                .setScale(4, RoundingMode.HALF_DOWN);
    }
}