/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.systempdv.service;

import com.systempdv.beans.ProductDAO;
import com.systempdv.model.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 * @author victo
 */
public class ProductService {

    private ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> findAll() {
        return productDAO.findAll();
    }

    public Optional<Product> findBySku(String sku) {
        if (sku == null || sku.trim().isEmpty()) {
            return Optional.empty();
        }
        return productDAO.findBySku(sku);
    }

    public boolean save(Product product) {
        if (!isValidProduct(product)) {
            return false;
        }
        return productDAO.save(product);
    }

    public boolean update(Product product) {
        if (!isValidProduct(product)) {
            return false;
        }
        
        return productDAO.update(product);
    }

    public boolean delete(String sku) {
        if (sku == null || sku.trim().isEmpty()) {
            return false;
        }

        return productDAO.delete(sku);
    }

    private boolean isValidProduct(Product product) {
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            System.err.println("O preço do produto não pode ser negativo ou nulo.");
            return false;
        }
        if (product.getStock() < 0) {
            System.err.println("O estoque não pode ser negativo.");
            return false;
        }

        return true;
    }
}
