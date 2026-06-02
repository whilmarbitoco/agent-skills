# SQLite Best Practices — Checklist

## Implementation

- [ ] Enable WAL mode: `PRAGMA journal_mode=WAL`
- [ ] Set busy timeout: `PRAGMA busy_timeout=5000`
- [ ] Use `PreparedStatement` for all parameterized queries
- [ ] Wrap related operations in explicit transactions
- [ ] Use `try-with-resources` for Connection, Statement, ResultSet
- [ ] Run `ANALYZE` after bulk inserts
- [ ] Add indexes on FK columns and frequently filtered columns
- [ ] Avoid `SELECT *` — specify columns explicitly

## Review

- [ ] No string concatenation in SQL
- [ ] No resource leaks (all JDBC objects in try-with-resources)
- [ ] WAL mode enabled
- [ ] Transactions used for multi-statement operations
- [ ] Indexes present on WHERE/JOIN/ORDER BY columns
