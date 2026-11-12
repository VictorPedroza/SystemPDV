/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.systempdv.beans;

import com.systempdv.database.Database;
import com.systempdv.model.PaymentMethod;
import com.systempdv.model.Product;
import com.systempdv.model.Sale;
import com.systempdv.model.SaleItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author victo
 */
public class SaleDAO {

    private Database database;

    public SaleDAO(Database database) {
        this.database = database;
    }

    public boolean save(Sale sale) {
        String sqlSale = "INSERT INTO sales (user_id, total_amount, payment_method, client, created_at) VALUES (?, ?, ?, ?, ?)";
        String sqlItem = "INSERT INTO sale_items (sale_id, product_sku, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = database.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtSale = conn.prepareStatement(sqlSale, Statement.RETURN_GENERATED_KEYS); PreparedStatement stmtItem = conn.prepareStatement(sqlItem)) {
                if (sale.getUserId() == null) {
                    stmtSale.setInt(1, 999);
                } else {
                    stmtSale.setInt(1, sale.getUserId());
                }

                stmtSale.setBigDecimal(2, sale.getTotalAmount());
                stmtSale.setString(3, sale.getPaymentMethod().name());

                if (sale.getClient() == null) {
                    stmtSale.setString(4, "");
                } else {
                    stmtSale.setString(4, sale.getClient());
                }

                stmtSale.setTimestamp(5, Timestamp.valueOf(sale.getCreatedAt()));
                stmtSale.executeUpdate();

                int saleId;

                try (ResultSet keys = stmtSale.getGeneratedKeys()) {
                    if (keys.next()) {
                        saleId = keys.getInt(1);
                        sale.setId(saleId);
                    } else {
                        throw new SQLException("Falha ao criar venda, ID não foi retornado");
                    }
                }

                for (SaleItem item : sale.getItems()) {
                    stmtItem.setInt(1, saleId);
                    stmtItem.setString(2, item.getProductSku());
                    stmtItem.setInt(3, item.getQtd());
                    stmtItem.setBigDecimal(4, item.getPrice());
                    stmtItem.setBigDecimal(5, item.getSubtotal());
                    stmtItem.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Transação abortada (Rollback). Erro ao salvar venda: " + e.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.err.println("Erro de conexão com o banco de dados: " + e.getMessage());
            return false;
        }
    }

    public List<Sale> getByUserId(Integer userId) {
        List<Sale> sales = new ArrayList<>();

        String sql = "SELECT id, total_amount, payment_method, created_at FROM sales WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Sale sale = new Sale(PaymentMethod.valueOf(rs.getString("payment_method")));
                    sale.setId(rs.getInt("id"));
                    sale.setTotalAmount(rs.getBigDecimal("total_amount"));
                    sale.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    sale.setUserId(userId);
                    sales.add(sale);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar vendas do operador: " + e.getMessage());
        }
        return sales;
    }

    public List<Sale> findAll() {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT id, total_amount, payment_method, user_id, client, created_at FROM sales ORDER BY created_at DESC";

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sale sale = new Sale(PaymentMethod.valueOf(rs.getString("payment_method")));
                sale.setId(rs.getInt("id"));
                sale.setTotalAmount(rs.getBigDecimal("total_amount"));
                sale.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                sale.setUserId(rs.getInt("user_id"));
                sale.setClient(rs.getString("client"));
                sales.add(sale);
            }
        } catch (SQLException e) {
            System.err.println("Erro no DAO ao listar produtos: " + e.getMessage());
        }

        return sales;
    }

    public Optional<Sale> findById(int id) {
        String sqlSale = "SELECT id, total_amount, payment_method, user_id, client, created_at FROM sales WHERE id = ?";
        String sqlItems = "SELECT id, product_sku, quantity, unit_price, subtotal FROM sale_items WHERE sale_id = ?";

        try (Connection conn = database.getConnection();PreparedStatement stmtSale = conn.prepareStatement(sqlSale)) {

            stmtSale.setInt(1, id);

            try (ResultSet rs = stmtSale.executeQuery()) {
                if (rs.next()) {
                    Sale sale = new Sale(PaymentMethod.valueOf(rs.getString("payment_method")));
                    sale.setId(rs.getInt("id"));
                    sale.setTotalAmount(rs.getBigDecimal("total_amount"));
                    sale.setUserId(rs.getInt("user_id"));
                    sale.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    sale.setClient(rs.getString("client"));

                    try (PreparedStatement stmtItems = conn.prepareStatement(sqlItems)) {
                        stmtItems.setInt(1, rs.getInt("id"));
                        try (ResultSet rsItems = stmtItems.executeQuery()) {
                            while (rsItems.next()) {
                                SaleItem item = new SaleItem(
                                        rsItems.getString("product_sku"),
                                        rsItems.getInt("quantity"),
                                        rsItems.getBigDecimal("unit_price")
                                );
                                item.setId(rsItems.getInt("id"));

                                sale.getItems().add(item);
                            }
                        }
                    }

                    return Optional.of(sale);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro no DAO ao buscar venda por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    public boolean delete(Integer id) {
        String sql = "DELETE FROM sales WHERE id = ?";

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro no DAO ao excluir venda: " + e.getMessage());
            return false;
        }
    }
}
