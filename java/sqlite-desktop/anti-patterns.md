# SQLite Desktop Anti-Patterns

## No WAL mode — concurrent reads blocked by writes

```java
// WRONG — default journal mode, readers blocked by single writer
dataSource.setUrl("jdbc:sqlite:pos.db");
```

**Enable WAL: `database.sqlUpdate("PRAGMA journal_mode=WAL").execute()`. Readers don't block writers.**

## Storing DB in project directory — lost on redeploy

```java
// WRONG — database inside project, wiped on git clean or redeploy
String url = "jdbc:sqlite:./pos.db";
```

**Store in user home: `Paths.get(System.getProperty("user.home"), ".simplepos", "pos.db")`.**

## No backup — single file corruption loses everything

```java
// WRONG — no backup strategy
```

**Daily backup: `Files.copy(dbPath, backupPath, StandardCopyOption.REPLACE_EXISTING)`. Keep last 7 days.**

## Multiple threads writing — SQLite corruption

```java
// WRONG — concurrent writes cause "database is locked"
executor.submit(() -> writeA());
executor.submit(() => writeB()); // may lock
```

**Queue writes via single-thread executor or use `PRAGMA busy_timeout = 30000`.**

## No migration strategy — schema changes break users

```java
// WRONG — drop and recreate tables on every version
database.sqlUpdate("DROP TABLE IF EXISTS product").execute();
```

**Use Ebean migrations: `1.sql`, `2.sql` numbered files. Track current version in schema_version table.**
