# SQLite Best Practices — Anti-Patterns

## 1. Not enabling WAL mode — readers block writers

```java
// WRONG — default DELETE journal mode, concurrent read/write fails
Connection conn = DriverManager.getConnection("jdbc:sqlite:app.db");
// Multiple threads: reader gets "database is locked" errors
```

```java
// FIX: enable WAL + busy timeout on connection init
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

Connection conn = DriverManager.getConnection("jdbc:sqlite:app.db");
try (Statement stmt = conn.createStatement()) {
    stmt.execute("PRAGMA journal_mode=WAL");
    stmt.execute("PRAGMA busy_timeout=5000");
}
```

## 2. String concatenation in SQL queries

```java
// WRONG — SQL injection vulnerability
String sql = "SELECT * FROM users WHERE name = '" + userInput + "'";
ResultSet rs = stmt.executeQuery(sql);
```

```java
// FIX: always use PreparedStatement
String sql = "SELECT id, name, email FROM users WHERE name = ?";
try (PreparedStatement ps = conn.prepareStatement(sql)) {
    ps.setString(1, userInput);
    try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            // process row
        }
    }
}
```

## 3. Not wrapping related operations in a transaction

```java
// WRONG — each statement is its own transaction (auto-commit)
stmt.executeUpdate("INSERT INTO orders (customer_id) VALUES (1)");
stmt.executeUpdate("UPDATE inventory SET qty = qty - 1 WHERE id = 42)");
// Crash between statements = inconsistent state
```

```java
// FIX: wrap in explicit transaction
conn.setAutoCommit(false);
try {
    stmt.executeUpdate("INSERT INTO orders (customer_id) VALUES (1)");
    stmt.executeUpdate("UPDATE inventory SET qty = qty - 1 WHERE id = 42)");
    conn.commit();
} catch (SQLException e) {
    conn.rollback();
    throw e;
} finally {
    conn.setAutoCommit(true);
}
```

## 4. Not closing JDBC resources

```java
// WRONG — resource leak
ResultSet rs = stmt.executeQuery("SELECT id FROM users");
while (rs.next()) { /* ... */ }
// rs never closed → cursor leak
```

```java
// FIX: try-with-resources for all JDBC objects
try (PreparedStatement ps = conn.prepareStatement("SELECT id FROM users");
     ResultSet rs = ps.executeQuery()) {
    while (rs.next()) {
        long id = rs.getLong("id");
    }
}
```

## 5. Running bulk inserts without ANALYZE afterward

```java
// WRONG — bulk insert, then queries choose suboptimal plans
loadMillionRows(conn); // insert 1M rows
// SELECT queries are slow because SQLite doesn't know table stats
```

```java
// FIX: run ANALYZE after bulk changes
loadMillionRows(conn);
try (Statement stmt = conn.createStatement()) {
    stmt.execute("ANALYZE"); // updates query planner statistics
}
```
