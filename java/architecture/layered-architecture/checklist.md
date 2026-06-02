# Layered Architecture — Checklist

## Implementation
- [ ] Each layer is a distinct package or module boundary
- [ ] Presentation imports Service, never Repository directly
- [ ] Service methods are the transaction boundary
- [ ] Repository returns domain objects or Optional, never null
- [ ] Domain classes have no framework annotations
- [ ] Constructor injection only — no field injection
- [ ] Cross-cutting utilities are in a shared package, not duplicated

## Review
- [ ] No upward dependencies (domain → service → presentation, never reverse)
- [ ] Business logic is in Service, not Controller or Filter
- [ ] Ebean/SQL queries live only in Repository classes
- [ ] No string concatenation in log statements
