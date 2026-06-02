# Domain-Driven Structure (Lite) — Checklist

## Implementation
- [ ] Value objects use `record` — immutable, validated in compact constructor
- [ ] Entities encapsulate behavior — no anemic getters/setters only
- [ ] Aggregates enforce invariants in methods (not in setters)
- [ ] Repository loads and persists whole aggregates
- [ ] Domain events are raised in aggregate methods, collected by application service
- [ ] Domain services are stateless, receive data via method arguments
- [ ] Package structure follows feature boundaries

## Review
- [ ] No mutable "value objects" without `record`
- [ ] No domain objects with EventPublisher or other infrastructure references
- [ ] Domain model has zero imports from web/controller packages
- [ ] Equals/hashCode works correctly for all value objects
