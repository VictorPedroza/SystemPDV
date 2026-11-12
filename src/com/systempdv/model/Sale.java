/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.systempdv.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author victo
 */
public class Sale {
    private Integer id;
    private Integer userId;
    private BigDecimal total;
    private PaymentMethod paymentMethod;
    private String client;
    private LocalDateTime createdAt;
    private List<SaleItem> items;
    
    public Sale(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        this.items = new ArrayList<>();
        this.total = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
    }
    
    public void addItem(SaleItem item) {
        this.items.add(item);
        this.total = this.total.add(item.getSubtotal());
    }
    
    // Getters & Setters
    public Integer getId() { return id; }
    public Integer getUserId() { return userId; }
    public BigDecimal getTotalAmount() { return total; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public String getClient() { return client; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<SaleItem> getItems() { return items; }

    public void setId(Integer id) { this.id = id; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public void setClient(String client) { this.client = client; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setTotalAmount(BigDecimal totalAmount) { this.total = totalAmount; }
    
    public void removeItem(int index) {
        SaleItem item = this.items.remove(index);
        this.total = this.total.subtract(item.getSubtotal());
    }
}
