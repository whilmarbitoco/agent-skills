---
name: sqlite-best-practices
description: >
  Extends agent's knowledge of using SQLite effectively in Java:
  WAL mode, transactions, indexes, prepared statements, and pitfalls.
  Use when working with embedded SQLite databases or optimizing query performance.
compatibility: Java 21+
metadata:
  domain: persistence
  level: intermediate
  stack: [java-21, sqlite-3.45, ebean-15, slf4j-2]
  version: "1.0.0"
---

# SQLite Best Practices

SQLite is an embedded, zero-config RDBMS ideal for
desktop applications, local caches, and small-to-medium datasets.
This skill covers Java-specific integration patterns.

## Concepts

- **WAL mode** — Write-Ahead Logging for concurrent reads + writes
- **Busy timeout** — wait for lock instead of failing immediately
- **Prepared statements** — reuse compiled SQL, prevent injection
- **Indexes** — add on columns used in WHERE, JOIN, ORDER BY
- **ANALYZE** — updates query planner statistics after bulk inserts
- **PRAGMA journal_mode=WAL** — enable at connection creation

## Rules

1. Always enable WAL mode: `PRAGMA journal_mode=WAL`.
2. Set busy timeout: `PRAGMA busy_timeout=5000` (5 seconds).
3. Use `PreparedStatement` or parameterized queries — never string concat.
4. Wrap related operations in explicit transactions (`BEGIN`/`COMMIT`).
5. Run `ANALYZE` after bulk inserts for optimal query plans.
6. Add indexes on foreign key columns and frequently filtered columns.
7. Use `try-with-resources` for all `Connection`, `Statement`, `ResultSet`.
8. Avoid `SELECT *` — specify only needed columns.

## Anti-patterns

See [anti-patterns.md](./anti-patterns.md).

## Related

- ebean-setup — Ebean + SQLite integration
- ebean-queries — query building
- database-migrations — schema versioning
