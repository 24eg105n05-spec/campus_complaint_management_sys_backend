package com.campuscomplaint.campuscomplaintbackend.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url:}")
    private String configuredUrl;

    @Value("${spring.datasource.username:}")
    private String configuredUsername;

    @Value("${spring.datasource.password:}")
    private String configuredPassword;

    @Bean
    public DataSource dataSource() {
        String dbUrl = System.getenv("DB_URL");
        String databaseUrl = System.getenv("DATABASE_URL");
        String finalUrl = (dbUrl != null && !dbUrl.isBlank()) ? dbUrl : databaseUrl;

        if (finalUrl == null || finalUrl.isBlank()) {
            finalUrl = configuredUrl;
        }

        HikariConfig config = new HikariConfig();

        // Handle postgresql:// URI format provided by cloud hosts like Render
        if (finalUrl != null && finalUrl.startsWith("postgresql://")) {
            try {
                URI uri = new URI(finalUrl);
                String userInfo = uri.getUserInfo();
                if (userInfo != null && userInfo.contains(":")) {
                    String[] parts = userInfo.split(":", 2);
                    config.setUsername(parts[0]);
                    config.setPassword(parts[1]);
                }
                int port = uri.getPort() > 0 ? uri.getPort() : 5432;
                String host = uri.getHost();
                String path = uri.getPath();
                config.setJdbcUrl("jdbc:postgresql://" + host + ":" + port + path);
            } catch (Exception e) {
                config.setJdbcUrl(finalUrl.replace("postgresql://", "jdbc:postgresql://"));
            }
        } else {
            config.setJdbcUrl(finalUrl);
            String user = System.getenv("DB_USERNAME");
            if (user == null || user.isBlank()) {
                user = configuredUsername;
            }
            config.setUsername(user);

            String pass = System.getenv("DB_PASSWORD");
            if (pass == null) {
                pass = configuredPassword;
            }
            config.setPassword(pass);
        }

        if (config.getJdbcUrl() != null && config.getJdbcUrl().contains("postgresql")) {
            config.setDriverClassName("org.postgresql.Driver");
        }

        return new HikariDataSource(config);
    }
}
