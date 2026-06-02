---
name: dependency-management
description: >
  Extends agent's knowledge of Maven dependency scopes, conflict
  resolution, BOMs, and exclusions. Use when adding, upgrading, or
  troubleshooting third-party libraries.
compatibility: Java 21+
metadata:
  domain: maven
  level: intermediate
  stack: [java-21, maven-3.9]
  version: "1.0.0"
---

# Dependency Management

Maven resolves transitive dependencies automatically. Conflicts are
resolved by "nearest definition wins" — the version closest to your
project in the dependency tree wins.

## Concepts

- **dependencyManagement** — centralizes versions; children omit `<version>`.
- **BOM (Bill of Materials)** — a POM with `<scope>import</scope>` that
  manages a library family's versions atomically.
- **Exclusions** — `<exclusion>` removes a specific transitive dep.
- **Scopes** — `compile`, `provided`, `runtime`, `test`, `import`, `system`.
- **Enforcer plugin** — bans duplicate or conflicting versions.

## Rules

1. Use `<dependencyManagement>` in the parent POM for every third-party dep.
2. Import BOMs (e.g., `ebean-bom`, `javafx-bom`) before other deps.
3. Children never specify `<version>` — always inherit from management.
4. Use `<exclusions>` to remove unwanted transitive deps (e.g., log4j → slf4j).
5. Prefer `mvn dependency:tree` over guessing which version wins.
6. Use `maven-enforcer-plugin` with `<dependencyConvergence>` in CI.
7. Pin plugin versions in `<pluginManagement>` — never rely on defaults.
8. Use `provided` scope for APIs supplied by the runtime (e.g., servlet-api).

## Anti-patterns

See [anti-patterns.md](./anti-patterns.md).

## Related

- multi-module-projects — reactor structure
- shading-and-packaging — relocating deps into uber-jar
- reproducible-builds — locking versions
