/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.systempdv.service;

import com.systempdv.database.Database;
import com.systempdv.model.Role;
import com.systempdv.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author victo
 */
public class AuthService {

    private Database database;

    public AuthService(Database database) {
        this.database = database;
    }

    public Optional<User> authenticate(String username, String password) {
        String sql = "SELECT id, role FROM users WHERE username = ? AND password = ? AND active = TRUE";

        try (Connection conn = database.getConnection()) {
            if (conn == null) {
                System.err.println("Falha na Autenticação: Conexação com o banco de dados");
                return Optional.empty();
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Role role = Role.valueOf(rs.getString("role"));
                        User user = new User(
                                rs.getInt("id"),
                                username,
                                role
                        );

                        return Optional.of(user);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro na Autenticação: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro no mapeamento de regra do usuário");
        }

        return Optional.empty();
    }
}
