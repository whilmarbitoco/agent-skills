---
name: database-migrations
description: >
  Extends agent's knowledge of Ebean database migrations for SQLite desktop apps.
  Use when managing schema changes, adding columns/tables, or versioning a database.
compatibility: Java 21+, Ebean 15+
metadata:
  domain: persistence
  level: intermediate
  stack: [java-21, ebean, sqlite]
  version: "1.0.0"
---

# Database Migrations

Schema evolution for SQLite desktop apps using Ebean migrations.
Never drop/recreate — always migrate forward.

## Core Concepts
- Numbered migration scripts: `1.sql`, `2.sql`, `3.sql`
- `db_migration` table tracks current version
- Ebean diff: generate migration from entity changes
- Always test on a copy of production data

## Rules
1. Number migrations sequentially — no gaps, no duplicates
2. Each migration is a single `.sql` file in `src/main/resources/dbmigration/`
3. Include `apply` and `undo` comments in each migration
4. Never `DROP`tables in production — use `ALTER TABLE ADD COLUMN` instead
5. Test migration on a backup before running on user data
6. Auto-migrate only in dev — in prod, run manually after backup

## Anti-patterns
- DROP TABLE IF EXISTS (data loss)
- Renaming columns (breaks existing data)
- Adding NOT NULL columns without defaults
- Running migrations without backup

## Relates to
- ebean-setup
- ebean-entities
- sqlite-desktop
