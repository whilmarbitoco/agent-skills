# Skill: Ebean Entity Modeling

Entity modeling with Ebean ORM for SQLite.

## Core Concepts
- `@Entity` + `@Table(name=...)` mapping
- `@OneToMany(mappedBy=...)` / `@ManyToOne` relationships
- `@Version` — optimistic locking, auto-incremented
- `@WhenCreated` / `@WhenModified` — audit timestamps
- `@Index` — index frequently queried columns
- `QBean` / `QProduct` — type-safe generated query beans
- `SQLitePlatform` — Ebean platform for SQLite

## Rules
1. Always add `@Version` for optimistic locking
2. Always add `@WhenCreated` and `@WhenModified` audit fields
3. Add `@Index` on all foreign key and frequently filtered columns
4. No business logic in entities — pure data holders + mapping
5. FetchType default: `@ManyToOne` EAGER, `@OneToMany` LAZY
6. Use `QProduct` for all queries — never raw JPQL/SQL in services
7. Define relationships bi-directionally with `mappedBy`

## Anti-patterns
- Business logic methods in `@Entity` classes
- Missing `@Version` (lost updates in concurrent use)
- `FetchType.EAGER` on `@OneToMany` (N+1 queries)
- Raw SQL strings outside repositories
- No `@Index` on lookup columns (full table scans)

## Relates to
- repository-pattern
- sqlite-desktop
- junit5-test
