# Layered Architecture — Quick Reference

| Layer | Package | Knows about | Returns |
|-------|---------|-------------|---------|
| Presentation | `.web`, `.controller`, `.cli` | Service | HTTP responses, UI updates |
| Service | `.service` | Repository, Domain | DTOs, domain objects |
| Repository | `.repository`, `.persistence` | Ebean, JDBC | Domain objects, `Optional` |
| Domain | `.model`, `.domain` | Nothing | Nothing (pure data + behavior) |

## Dependency rule
```
Presentation → Service → Repository → Domain
```
Arrows only point down. Never up.
