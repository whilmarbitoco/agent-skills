# Database Migrations — Anti-Patterns

## Problem 1: Modifying existing migrations

```sql
-- WRONG — never modify a migration that has been run
ALTER TABLE products ADD COLUMN old_price DECIMAL(10,2);
-- What about environments where this migration already ran?
```

-- FIX — create new migration files, never modify old ones
-- V1__create_products.sql (original, never touch)
-- V2__add_old_price.sql (new file)
ALTER TABLE products ADD COLUMN old_price DECIMAL(10,2);
```

## Problem 2: No down/out-of-order migrations

```sql
-- WRONG — irreversibly drop a column in a forward migration
ALTER TABLE products DROP COLUMN price;
-- No way to undo, and no rollback script
```

```sql
-- FIX — provide rollback or out-of-order migration
-- V3__remove_price.sql (out-of-order migration)
ALTER TABLE products DROP COLUMN price;
-- Rollback statement in migration config or manual script
ALTER TABLE products ADD COLUMN price DECIMAL(10,2);
```

## Problem 3: Large batches of data in migrations

```sql
-- WRONG — INSERT 100K rows in a migration
INSERT INTO products SELECT * FROM legacy_products; -- blocks DB for minutes
```

```sql
-- FIX — separate schema changes from data migration
-- V4__add_products_table.sql (schema only)
CREATE TABLE products_new (...);
-- Run data migration separately (application code, batched)
-- INSERT INTO products_new SELECT * FROM legacy_products LIMIT 1000;
```
