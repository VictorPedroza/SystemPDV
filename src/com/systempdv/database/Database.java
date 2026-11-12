/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.systempdv.database;

import com.systempdv.service.ApplicationContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author victo
 */
public class Database {

    private String url;
    private String root;
    private String password;

    private ApplicationContext context;

    public Database(ApplicationContext context) {
        this.url = context.getProperty("db.url");
        this.root = context.getProperty("db.root");
        this.password = context.getProperty("db.password");
    }

    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(url, root, password);
            System.out.println("Conexão realizada com sucesso");

            return conn;
        } catch (Exception e) {
            System.err.println("Erro ao conectar com banco de dados: " + e.getMessage());
            return null;
        }
    }
}
