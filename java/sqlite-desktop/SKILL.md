# Skill: SQLite for Desktop Apps

SQLite configuration for Java desktop applications.

## Core Concepts
- WAL mode — `PRAGMA journal_mode=WAL` (readers don't block writers)
- File location — `~/.appname/data.db` in user home
- `jdbc:sqlite:~/.appname/data.db` JDBC URL
- Single-writer limitation — serialize writes via single-thread executor
- Backup — `VACUUM INTO` or file copy on shutdown

## Rules
1. Always enable WAL mode on startup: `PRAGMA journal_mode=WAL`
2. Store database in user home: `~/.appname/data.db`
3. Perform daily backup via `VACUUM INTO` or file copy
4. Don't write concurrently — use single-thread executor for writes
5. Set busy timeout: `PRAGMA busy_timeout=5000`
6. Use `SQLitePlatform` in Ebean server config

## Anti-patterns
- Default rollback journal (no WAL) — poor concurrent read performance
- Storing DB in working directory (lost on app restart)
- Multiple threads writing simultaneously (database locked errors)
- No backup strategy (data loss on corruption)

## Relates to
- ebean-entity-modeling
- repository-pattern
- junit5-test
