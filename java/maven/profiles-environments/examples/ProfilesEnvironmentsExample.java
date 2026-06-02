package com.example.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads configuration from application.properties.
 * Properties are resolved at build time via Maven resource filtering.
 * Environment-specific values come from the active Maven profile or
 * environment variables.
 */
public record AppConfig(String environment, String dbUrl, int dbPoolSize) {

    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    public static AppConfig load() {
        Properties props = new Properties();
        try (InputStream is = AppConfig.class.getResourceAsStream(
                "/application.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            log.warn("Could not load application.properties, using defaults", e);
        }

        String env = props.getProperty("app.env", "dev");
        String dbUrl = resolve("DB_URL",
            props.getProperty("app.db.url", "jdbc:sqlite:local.db"));
        int poolSize = Integer.parseInt(
            props.getProperty("app.db.pool", "4"));

        log.info("Config loaded: env={}, dbUrl={}", env, dbUrl);
        return new AppConfig(env, dbUrl, poolSize);
    }

    private static String resolve(String envVar, String fallback) {
        String value = System.getenv(envVar);
        return value != null ? value : fallback;
    }
}
