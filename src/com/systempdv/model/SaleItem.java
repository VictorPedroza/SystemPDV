/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.systempdv.model;

import java.math.BigDecimal;

/**
 *
 * @author victo
 */
public class SaleItem {
    private Integer id;
    private String sku;
    private int qtd;
    private BigDecimal price;
    private BigDecimal subtotal;
    
    public SaleItem(String sku, int qtd, BigDecimal price) {
        this.sku = sku;
        this.qtd = qtd;
        this.price = price;
        this.subtotal = price.multiply(new BigDecimal(qtd));
    }
    
    public Integer getId() { return id; }
    public String getProductSku() { return sku; }
    public int getQtd() { return qtd; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getSubtotal() { return subtotal; }
    
    public void setId(Integer id) {
        this.id = id;
    }
}
