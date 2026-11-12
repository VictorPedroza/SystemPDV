/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.systempdv.service;

import com.systempdv.model.User;
import java.io.IOException;
import java.nio.file.Path;

/**
 *
 * @author victo
 */
public class ApplicationContext {
    private ApplicationConfig config;
    private User user;
    
    public ApplicationContext(ApplicationConfig config) {
        this.config = config;
    }
    
    public static ApplicationContext create(Path dir) throws IOException {
        ApplicationConfig config = ApplicationConfig.load(dir);
        return new ApplicationContext(config);
    }

    public ApplicationContext() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public ApplicationConfig getConfig() {
        return config;
    }
    
    public String getProperty(String key) {
        return config.getProperty(key);
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public User getUser() { return user; }
}
