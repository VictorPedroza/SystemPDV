/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author victo
 */
public class Employee extends User {
    private String username;
    private String password;
    private String role;
    
     public Employee(String id, String name, String cpf, String username, String password, String role) {
         super(id, name, cpf);
         this.username = username;
         this.password = password;
         this.role = role;
     }
     
     // Gettes & Setters
     public String getUsername() { return username; }
     public String getPassword() { return password; }
     public String getRole() { return role; }
     
     public void setUsername(String username) { this.username = username; }
     public void setPassword(String password) { this.password = password; }
     public void setRole(String role) { this.role = role; }
}
