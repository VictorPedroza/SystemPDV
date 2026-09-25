/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author victo
 */
public class Database {
    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/system_pdv?useTimezone=true&serverTimezone=UTC","root","");
            System.out.println("Conexão realizada com sucesso!");
            
            return conn;
            
        } catch (Exception e) {
            System.out.println("Erro ao conectar no banco de dados: " + e.getMessage());
            return null;
        }
    }
}
