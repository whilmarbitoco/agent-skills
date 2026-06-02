package com.pos.security.secrets;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Secrets management: load from environment, file, or keyring.
 * Never hardcode. Support rotation. Mask in logs.
 */
public class SecretsManagement {

    // Load from environment variable
    public static String fromEnv(String name) {
        String value = System.getenv(name);
        if (value == null) throw new RuntimeException("Missing env var: " + name);
        return value;
    }

    // Load from file (Docker secrets, Kubernetes secrets)
    public static String fromFile(String path) {
        try {
            return Files.readString(Path.of(path)).trim();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read secret file: " + path, e);
        }
    }

    // Mask for logging
    public static String mask(String secret) {
        if (secret == null || secret.length() <= 4) return "****";
        return secret.substring(0, 4) + "****";
    }

    // Versioned secrets with rotation support
    static class SecretStore {
        private final Map<String, String> secrets = new LinkedHashMap<>();

        public void load(Properties props) {
            props.forEach((k, v) -> secrets.put(k.toString(), v.toString()));
        }

        public String get(String key) {
            String value = secrets.get(key);
            if (value == null) throw new RuntimeException("Secret not found: " + key);
            return value;
        }

        public String get(String key, String defaultValue) {
            return secrets.getOrDefault(key, defaultValue);
        }

        public void put(String key, String value) {
            secrets.put(key, value);
        }
    }
}
