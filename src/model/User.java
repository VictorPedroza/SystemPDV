package model;

/**
 *
 * @author victo
 */
public class User {
    private String id;
    private String name;
    private String cpf;
    
    public User(String id, String name, String cpf) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
    }
    
    // Getters & Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCpf() { return cpf; } 
    
    public void setName(String name) { this.name = name; }
}
