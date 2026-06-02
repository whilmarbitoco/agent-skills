---
name: immutability
description: >
  Extends agent's knowledge of immutability patterns in Java: defensive copying,
  with-er methods, and choosing between records and classes. Use when designing
  value objects, preventing mutation of internal state, or refactoring mutable classes.
compatibility: Java 21+
metadata:
  domain: core-java
  level: beginner
  stack: [java-21]
  version: "1.0.0"
---

# Immutability Patterns

Immutable objects are safer, easier to reason about, and inherently thread-safe.
Java 21 provides records and `List.of()` / `Map.of()` / `Set.of()` for
lightweight immutable values.

## Concepts

- **Records** — shallowly immutable data carriers (all fields are final)
- **Defensive copy** — copy mutable inputs in constructors; copy mutable outputs in getters
- **With-er methods** — `withX(newValue)` returns a new instance with one field changed
- **`List.copyOf()`, `Map.copyOf()`** — unmodifiable copies at runtime
- **When to use records vs classes** — records for values, classes for entities with behavior/identity

## Rules

1. Make objects immutable by default. Add mutability only when required.
2. Defensively copy all mutable constructor parameters (collections, arrays, dates).
3. Return unmodifiable views or copies from getters — never the internal reference.
4. Use with-er methods for "mutation" of immutable objects.
5. Use records for pure data; use classes when you need encapsulation beyond the data.
6. Mark fields `final`; never provide setters on immutable objects.

## Anti-patterns

See [anti-patterns.md](./anti-patterns.md).

## Related

- records-sealed — records, sealed interfaces
- collections-best-practices — List.of(), copyOf()
