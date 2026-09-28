package beans;

import config.database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Product;

/**
 *
 * @author victo
 */
public class ProductDAO {

    private Database database;
    private Connection conn;

    public ProductDAO() {
        this.database = new Database();
        this.conn = this.database.getConnection();
    }

    public void insert(Product product) {
        String sql = "INSERT INTO product (description, barcode, sku, value, active, ncm) VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);

            stmt.setString(1, product.getDescription());
            stmt.setString(2, product.getBarcode());
            stmt.setString(3, product.getSku());
            stmt.setBigDecimal(4, product.getValue());
            stmt.setBoolean(5, product.isActive());
            stmt.setInt(6, product.getNcm());

            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir pessoa: " + e.getMessage());
        }
    }

    public void delete(String sku) {
        String sql = "DELETE FROM product WHEE sku = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, sku);
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir pessoa: " + e.getMessage());
        }
    }

    public void edit(Product product) {
        String sql = "UPDATE product SET description = ?, barcode = ?, value = ?, active = ?, ncm = ? WHERE sku = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, product.getDescription());
            stmt.setString(2, product.getBarcode());
            stmt.setBigDecimal(3, product.getValue());
            stmt.setBoolean(4, product.isActive());
            stmt.setInt(5, product.getNcm());
            stmt.setString(6, product.getSku());

            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir pessoa: " + e.getMessage());
        }
    }

    public Product get(String sku) {
        String sql = "SELECT * FROM product WHERE sku = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, sku);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Product p = new Product(
                        rs.getString("description"),
                        rs.getString("barcode"),
                        rs.getString("sku"),
                        rs.getBigDecimal("value"),
                        rs.getObject("ncm", Integer.class)
                );

                return p;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar produto: " + e.getMessage());
        }

        return null;
    }

    public List<Product> getAll() {
        String sql = "SELECT * FROM product";

        List<Product> products = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Product p = new Product(
                        rs.getString("description"),
                        rs.getString("barcode"),
                        rs.getString("sku"),
                        rs.getBigDecimal("value"),
                        rs.getObject("ncm", Integer.class)
                );

                products.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar produtos: " + e.getMessage());
        }

        return products;
    }
}
