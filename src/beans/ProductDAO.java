package beans;

import config.database.Database;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
}
