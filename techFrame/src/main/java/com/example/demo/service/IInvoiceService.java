package com.example.demo.service;

import com.example.demo.models.Invoice;
import com.example.demo.models.InvoiceItem;

import java.util.List;

public interface IInvoiceService {
    Invoice createInvoice(List<InvoiceItem> items);


}