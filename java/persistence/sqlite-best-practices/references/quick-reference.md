# SQLite Best Practices — Quick Reference

| PRAGMA | Purpose |
|---|---|
| `journal_mode=WAL` | Write-Ahead Logging — concurrent reads + writes |
| `busy_timeout=5000` | Wait up to 5s for locks instead of failing |
| `foreign_keys=ON` | Enforce FK constraints (off by default) |
| `synchronous=NORMAL` | Balance durability and speed in WAL mode |

| Problem | Solution |
|---|---|
| SQL injection | `PreparedStatement` with `?` placeholders |
| `database is locked` | WAL mode + busy_timeout |
| Slow queries after bulk insert | Run `ANALYZE` |
| Resource leak | `try-with-resources` on Connection, Statement, ResultSet |
| Inconsistent state | Explicit `conn.setAutoCommit(false)` + `commit()`/`rollback()` |

| When | Action |
|---|---|
| App startup | Enable WAL + busy_timeout + foreign_keys |
| Bulk insert | Wrap in single transaction + `ANALYZE` after |
| Concurrent access | WAL mode allows one writer + many readers |
| Query tuning | Add indexes on WHERE/JOIN/ORDER BY columns |
