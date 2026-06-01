# Skill: Repository Pattern

Repository abstraction over Ebean ORM.

## Core Concepts
- `Repository<T, ID>` interface — defined in service/domain layer
- `EbeanRepository<T, ID>` implementation — in persistence layer
- `Optional<T>` returns — never return `null` from query methods
- Query methods — `findByName`, `findByCategory`, `findActive`
- Pagination — `setFirstRow()` / `setMaxRows()` on `Query<T>`

## Rules
1. Interface lives in service/domain layer; implementation in persistence layer
2. All query methods return `Optional<T>` or `List<T>` — never `null`
3. All Ebean queries must be inside repository implementations
4. No raw SQL strings outside repository classes
5. Use `Ebean.find(Entity.class)` inside implementations
6. Pagination via `query.setFirstRow(offset).setMaxRows(limit)`

## Anti-patterns
- `Ebean.find(Product.class)` in service classes (leaks persistence)
- Returning `null` for "not found" (use `Optional.empty()`)
- Business logic in repository (repository = query only)
- Multiple repository interfaces for same entity (consolidate)

## Relates to
- ebean-entity-modeling
- sqlite-desktop
- junit5-test
