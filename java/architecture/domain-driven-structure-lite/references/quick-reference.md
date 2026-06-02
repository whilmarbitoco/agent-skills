# Domain-Driven Structure (Lite) — Quick Reference

| DDD Block | Java construct | Key trait |
|-----------|---------------|-----------|
| Value Object | `record` | Immutable, validated in constructor |
| Entity | Class with `id` | Mutable, identity-based equality |
| Aggregate Root | Entity | Consistency boundary |
| Domain Event | `record` | Past tense, immutable, no behavior |
| Domain Service | Class | Stateless, pure logic |
| Repository Interface | Interface | Defined in domain, implemented in infra |

## Event pattern
```java
// Raise inside entity
domainEvents.add(new OrderConfirmed(id, Instant.now()));

// Collect in application service
var events = order.getDomainEvents();
events.forEach(bus::publish);
order.clearDomainEvents();
```
