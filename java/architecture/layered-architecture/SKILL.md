---
name: layered-architecture
description: >
  Extends agent's knowledge of classic layered (n-tier) Java architecture.
  Use when structuring server-side or desktop apps with clear separation:
  presentation → service → repository → database.
compatibility: Java 21+
metadata:
  domain: architecture
  level: beginner
  stack: [java-21, slf4j, ebean]
  version: "1.0.0"
---

# Layered Architecture

Separate concerns into strict top-down layers. Each layer depends only on the one below.

## Layers (top → bottom)

1. **Presentation** — HTTP handlers, JavaFX controllers, CLI parsers. No business logic.
2. **Service** — Business rules, transactions, validations. Orchestrates repos.
3. **Repository** — Data access (Ebean queries, SQLite). Returns domain objects.
4. **Domain / Model** — Entities, value objects (records), enums.

## Rules

- Presentation never touches Repository directly — goes through Service.
- Services are transactional boundaries; repos are not.
- Domain objects carry NO framework annotations (keep Ebean annotations in entity classes).
- Constructor injection throughout; no field injection.
- Cross-cutting concerns via utility classes, not aspect magic.

## See also

- feature-based-packaging — alternative packaging strategy
- service-repository-pattern — deeper dive into service/repo layer
