package com.example.demo.service;

import com.example.demo.models.Invoice;
import com.example.demo.models.InvoiceItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InvoiceService implements IInvoiceService {

    public Invoice createInvoice(List<InvoiceItem> items) {
        Invoice invoice = new Invoice();

        for (InvoiceItem item : items) {
            int quantity = item.getQuantity();
            if (quantity == 0) {
                continue;
            }

            addItemsToInvoice(item, invoice);
        }

        return invoice;
    }

    private boolean addItemsToInvoice(InvoiceItem item, Invoice invoice) {
        boolean isSPI = isSingleProductInvoice(item, invoice);

        if (isSPI) {
            return addSingleProductToInvoice(item, invoice);
        } else {
            return addProductToInvoice(item, invoice);
        }
    }


    private boolean isSingleProductInvoice(InvoiceItem item, Invoice invoice) {
        BigDecimal productTotalPrice = item.getProduct().getTotalPrice();
        return productTotalPrice.compareTo(Invoice.MAX_AMOUNT) > 0 && invoice.getProducts().isEmpty();
    }

    private boolean addSingleProductToInvoice(InvoiceItem item, Invoice invoice) {
        boolean isAdded = invoice.addSingleProduct(new InvoiceItem(item.getProduct(), 1));
        if (isAdded) {
            item.setQuantity(item.getQuantity() - 1);
        }
        return isAdded;
    }