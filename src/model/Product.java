package model;

import java.math.BigDecimal;

/**
 *
 * @author victo
 */
public class Product {
    private String description;
    private String barcode;
    private String sku;
    private BigDecimal value;
    private Boolean active;
    private Integer ncm;
    
    public Product(String description, String barcode, String sku, BigDecimal value, Integer ncm) {
        this.description = description;
        this.barcode = barcode;
        this.sku = sku;
        this.value = value;
        this.active = true;
        this.ncm = ncm;
    }
    
    // Getters & Setters
    public String getDescription() { return description; }
    public String getBarcode() { return barcode; }
    public String getSku() { return sku; }
    public BigDecimal getValue() { return value; }
    public Boolean isActive() { return active; }
    public Integer getNcm() { return ncm; }
    
    public void setDescription(String description) { this.description = description; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public void setSku(String sku) { this.sku = sku; }
    public void setValue(BigDecimal value) { this.value = value; }
    public void setActive(Boolean active) { this.active = active; }
    public void setNcm(Integer ncm) { this.ncm = ncm; }
            
}
