# Skill: Record Value Objects

Domain modeling with records and sealed classes.

## Core Concepts
- `record` — immutable data carrier with auto `equals`/`hashCode`/`toString`
- Compact constructor — validation in `compact ctor` (no `this.` assignments)
- `sealed interface` + `permits` — closed type hierarchies
- Pattern matching — `instanceof` with binding variable, `switch` on sealed types
- Exhaustive switch — compiler verifies all permitted types are covered

## Rules
1. All value objects must be `record` types
2. Use `sealed interface` + `permits` for closed hierarchies (payment types, statuses)
3. Validate in compact constructor — throw `IllegalArgumentException` for invalid data
4. Use exhaustive `switch` on sealed types — not `instanceof` chains
5. Amounts use `BigDecimal` + `Currency` (PHP Philippine Peso)
6. Records are immutable — no setters, no mutation methods

## Anti-patterns
- `instanceof` chains instead of exhaustive `switch`
- Mutable value objects (use `record`)
- Missing compact constructor validation
- `sealed` without `permits` (useless — compiler can't verify exhaustiveness)

## Relates to
- ebean-entity-modeling
- repository-pattern
- junit5-test
