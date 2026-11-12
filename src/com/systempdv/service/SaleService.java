/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.systempdv.service;

import com.systempdv.beans.SaleDAO;
import com.systempdv.model.Sale;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author victo
 */
public class SaleService {
    private SaleDAO sDAO;
    
    public SaleService(SaleDAO sDAO) {
        this.sDAO = sDAO;
    }
    
    public boolean process(Sale sale) {
        if (sale.getItems() == null || sale.getItems().isEmpty()) {
            System.err.println("Não é possível finalizar uma venda sem itens.");
            return false;
        }
        return sDAO.save(sale);
    }
    
    public List<Sale> findAll() {
        return sDAO.findAll();
    }
    
    public Optional<Sale> findById(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        return sDAO.findById(id);
    }
    
    public boolean delete(Integer id) {
        if (id == null) {
            return false;
        }

        return sDAO.delete(id);
    }
    
    
}
