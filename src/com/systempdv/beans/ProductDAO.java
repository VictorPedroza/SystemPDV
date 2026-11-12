/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.systempdv.beans;

import com.systempdv.database.Database;
import com.systempdv.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author victo
 */
public class ProductDAO {

    private Database database;

    public ProductDAO(Database database) {
        this.database = database;
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT sku, name, category, price, stock FROM products ORDER BY name";

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getString("sku"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro no DAO ao listar produtos: " + e.getMessage());
        }

        return products;
    }

    public Optional<Product> findBySku(String sku) {
        String sql = "SELECT sku, name, category, price, stock FROM products WHERE sku = ?";

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, sku);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Product product = new Product(
                        rs.getString("sku"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock")
                );
                return Optional.of(product);
            }
        } catch (SQLException e) {
            System.err.println("Erro no DAO ao buscar produto por SKU: " + e.getMessage());
        }
        return Optional.empty();
    }

    public boolean save(Product product) {
        String sql = "INSERT INTO products (sku, name, category,  price, stock) VALUES (?,?,?,?,?)";

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, product.getSku());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getCategory());
            stmt.setBigDecimal(4, product.getPrice());
            stmt.setInt(5, product.getStock());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro no DAO ao salvar produto: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Product product) {
        String sql = "UPDATE products SET name = ?, category = ?, price = ?, stock = ? WHERE sku = ?";

        try (Connection conn = database.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setBigDecimal(3, product.getPrice());
            stmt.setInt(4, product.getStock());
            stmt.setString(5, product.getSku());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro no DAO ao atualizar produto: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(String sku) {
        String sql = "DELETE FROM products WHERE sku = ?";

        try (Connection conn = database.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, sku);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro no DAO ao excluir produto: " + e.getMessage());
            return false;
        }
    }
}
