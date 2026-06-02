# Immutability — Checklist

## Implementation

- [ ] Make all fields `final`
- [ ] Defensively copy mutable constructor parameters (List, Map, Date, arrays)
- [ ] Return unmodifiable copies/views from getters — never internal references
- [ ] Use with-er methods (`withX()`) for derived immutable instances
- [ ] Prefer records for pure data value objects
- [ ] Use `List.of()`, `Map.of()`, `Set.of()` for inline immutable collections

## Review

- [ ] No public setters on immutable objects
- [ ] No direct references to mutable internal state exposed
- [ ] All Date/Collection/array constructor parameters are copied
- [ ] Classes that should be records are records
