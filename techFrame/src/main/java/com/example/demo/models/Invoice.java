package com.example.demo.models;


import com.example.demo.IdGenerator;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Data
public class Invoice {

    //ose @Id prej Id generator per increment
    private String id;
    private BigDecimal vat;
    private BigDecimal subTotal;
    private BigDecimal totalAmount;

    private List<InvoiceItem> products;

    public static final BigDecimal MAX_AMOUNT = new BigDecimal(500);
    public static final Integer PRODUCT_LIMIT = 50;
    private static final String PREFIX = "INV";
    private static final int ID_LENGTH = 3;
    private static int counter = 1;


    public Invoice() {
        this(new ArrayList<>(), new BigDecimal(0), new BigDecimal(0));
    }

    public Invoice(List<InvoiceItem> products, BigDecimal subTotal, BigDecimal totalAmount) {
        this.id = IdGenerator.generateId(PREFIX, ID_LENGTH, counter++);
        this.products = products;
        this.subTotal = subTotal;
        this.totalAmount = totalAmount;
    }

    public boolean addProduct(InvoiceItem item) {
        if (item != null && item.getQuantity() > 0) {
            if (canAddProduct(item)) {
                int index = products.indexOf(item);
                if (index == -1) {
                    products.add(item);
                } else {
                    InvoiceItem existingItem = products.get(index);
                    Integer totalQuantity = item.getQuantity() + existingItem.getQuantity();
                    item.setQuantity(totalQuantity);
                    products.set(index, item);
                }
                totalAmount = calculateTotalAmount();
                subTotal = calculateSubTotal();
                vat = totalAmount.subtract(subTotal);
                return true;
            }
        }
        return false;
    }



    public boolean addSingleProduct(InvoiceItem item) {
        if (products.size() > 0) {
            return false;
        }
        item.setQuantity(1);

        products.add(item);
        totalAmount = calculateTotalAmount();
        subTotal = calculateSubTotal();
        vat = totalAmount.subtract(subTotal);

        return true;
    }

    public boolean canAddProduct(InvoiceItem item) {
        if (totalAmount.compareTo(MAX_AMOUNT) > 0) return false;

        int index = products.indexOf(item);
        if (index != -1) {
            InvoiceItem currentItem = products.get(index);
            Integer currentQuantity = currentItem.getQuantity();
            if (currentQuantity == null) currentQuantity = 0;

            int totalQuantity = currentQuantity + item.getQuantity();
            if (totalQuantity > PRODUCT_LIMIT) return false;
        }
        BigDecimal price = item.getProduct().getTotalPrice().multiply(new BigDecimal(item.getQuantity()));

        BigDecimal totalPrice = totalAmount.add(price);
        return totalPrice.compareTo(MAX_AMOUNT) <= 0;
    }

    public List<InvoiceItem> getProducts() {
        return products;
    }

    public BigDecimal calculateTotalAmount() {
        BigDecimal total = new BigDecimal(0);
        for (InvoiceItem item : products) {
            BigDecimal productPrice = item.getProduct().getTotalPrice();
            int quantity = item.getQuantity();

            total = total.add(productPrice.multiply(new BigDecimal(quantity)));
        }
        return total.setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal calculateSubTotal() {
        BigDecimal subTotal = new BigDecimal(0);
        for (InvoiceItem item : products) {
            BigDecimal productPrice = item.getProduct().getDiscountPrice();
            int quantity = item.getQuantity();

            subTotal = subTotal.add(productPrice.multiply(new BigDecimal(quantity)));
        }
        return subTotal.setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

}