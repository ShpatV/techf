package com.example.demo.models;
import lombok.Data;
import java.math.BigDecimal;
import java.math.RoundingMode;
@Data
public class Product {
    private final String name;
    private final BigDecimal price;
    private final BigDecimal discount;
    private final BigDecimal vat;
    public Product(String name, BigDecimal price, BigDecimal vat) {
        this(name, price, BigDecimal.ZERO, vat);
    }
    public Product(String name, BigDecimal price, BigDecimal discount, BigDecimal vat) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.vat = vat;
    }
    public BigDecimal getTotalPrice() {
        BigDecimal discountedPrice = price.subtract(discount);
        BigDecimal vatMultiplier = vat.divide(BigDecimal.valueOf(100), 4, RoundingMode.DOWN);
        return discountedPrice.add(discountedPrice.multiply(vatMultiplier).setScale(3, RoundingMode.HALF_DOWN));
    }
    public BigDecimal getDiscountPrice() {
        return price.subtract(discount);
    }
}
