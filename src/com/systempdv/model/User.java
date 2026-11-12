/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.systempdv.model;

/**
 *
 * @author victo
 */
public class User {
    private Integer id;
    private String username;
    private Role role;
    
    public User(Integer id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public User(String username, Role role) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Getters
    public Integer getId() { return id; }
    public String getUsername() { return username; }
    public Role getRole() { return role; }
    public boolean isAdmin() { return role == Role.ADMIN; }
    public boolean isCashier() { return role == Role.CASHIER; }
    
}
