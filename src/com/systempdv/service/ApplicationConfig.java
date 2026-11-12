package com.systempdv.service;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ApplicationConfig {

    private Path dir;
    private String storeName;
    private Properties properties;

    public ApplicationConfig(Path dir, String name, Properties properties) {
        this.dir = dir;
        this.storeName = name;
        this.properties = properties;
    }

    public static ApplicationConfig load(Path dir) throws IOException {

        Files.createDirectories(dir);

        Path file = dir.resolve("application.properties");

        if (!Files.exists(file)) {
            try (Writer w = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {

                w.write("store.name=SystemPDV\n");
                w.write("default.system=CAIXA\n\n");
                
                w.write("db.url=\n");
                w.write("db.root=\n");
                w.write("db.password=\n");
            }
        }

        Properties props = new Properties();

        try (Reader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            props.load(r);
        }

        return new ApplicationConfig(
                dir,
                props.getProperty("store.name", "SystemPDV").trim(),
                props
        );
    }

    public Path getDir() {
        return dir;
    }

    public String getStoreName() {
        return storeName;
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
