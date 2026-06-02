package com.pos.persistence.migration;

import java.util.*;

/**
 * Database migration pattern: versioned, immutable migration scripts.
 * Each migration is a separate file, never modified after creation.
 * Supports up and down migrations for rollback.
 */
public class DatabaseMigrationDemo {

    // Migration naming convention:
    // V1__create_users.sql
    // V2__create_products.sql
    // V3__add_price_column.sql
    // V4__add_barcode_index.sql (out-of-order migration)

    record Migration(String version, String description, String upSql, String downSql) {}

    class MigrationRunner {
        private final List<Migration> migrations = new ArrayList<>();
        private final Map<String, String> executed = new LinkedHashMap<>();

        public void register(Migration m) { migrations.add(m); }

        public void migrate() {
            for (Migration m : migrations) {
                if (!executed.containsKey(m.version())) {
                    execute(m.upSql());
                    executed.put(m.version(), m.description());
                    log("Applied: " + m.version() + " - " + m.description());
                }
            }
        }

        public void rollback(String targetVersion) {
            List<String> toRollback = new ArrayList<>(executed.keySet());
            Collections.reverse(toRollback);
            for (String version : toRollback) {
                if (version.compareTo(targetVersion) <= 0) break;
                Migration m = findMigration(version);
                execute(m.downSql());
                executed.remove(version);
                log("Rolled back: " + version);
            }
        }

        private Migration findMigration(String version) {
            return migrations.stream()
                .filter(m -> m.version().equals(version))
                .findFirst()
                .orElseThrow();
        }

        private void execute(String sql) { /* db.execute(sql); */ }
        private void log(String msg) { System.out.println(msg); }
    }

    // Example migrations
    void setupMigrations() {
        MigrationRunner runner = new MigrationRunner();
        runner.register(new Migration("1", "Create users table",
            "CREATE TABLE users (id TEXT PRIMARY KEY, username TEXT NOT NULL, password_hash TEXT NOT NULL, role TEXT NOT NULL)",
            "DROP TABLE users"));
        runner.register(new Migration("2", "Create products table",
            "CREATE TABLE products (id TEXT PRIMARY KEY, name TEXT NOT NULL, barcode TEXT UNIQUE, price DECIMAL(10,2) NOT NULL, qty INTEGER NOT NULL DEFAULT 0)",
            "DROP TABLE products"));
        runner.register(new Migration("3", "Add price history",
            "CREATE TABLE price_history (id INTEGER PRIMARY KEY AUTOINCREMENT, product_id TEXT NOT NULL, old_price DECIMAL(10,2), new_price DECIMAL(10,2), changed_at TEXT NOT NULL)",
            "DROP TABLE price_history"));
        runner.migrate();
    }
}
