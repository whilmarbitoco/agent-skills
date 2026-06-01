# Agent Skills — Usage Guide

This directory contains Java 21 LTS agent skills following the
[Agent Skills](https://agentskills.io) open standard.

## How to Use

### Auto-detection
SKILL.md files use YAML frontmatter `description` fields to declare when they apply.
An agent reads all `description` fields at startup (~100 tokens each) and loads
the full SKILL.md body only when a task matches.

### Manual Invocation
When a skill is relevant, read its SKILL.md:
```
read_file("java/javafx-threading/SKILL.md")
```

Then load supporting files as needed:
```
read_file("java/javafx-threading/anti-patterns.md")
read_file("java/javafx-threading/checklist.md")
read_file("java/javafx-threading/examples/task-example.java")
read_file("java/javafx-threading/references/threading-reference.md")
```

### Progressive Disclosure
- **SKILL.md** (~30 lines) — concepts, rules, anti-patterns. Always load first.
- **anti-patterns.md** — wrong code + fix. Load when reviewing code.
- **checklist.md** — implementation + review checklists. Load before writing code.
- **examples/** — real Java code. Load when implementing a specific pattern.
- **references/** — quick reference tables. Load for API details.

### Execution Order
1. Match task to skill via `description` frontmatter
2. Load SKILL.md body into context
3. Load SKILL.md `checklist.md` for implementation steps
4. Load `examples/` for copy-paste patterns
5. Write code following rules and conventions
6. Review code against `anti-patterns.md`
7. Verify against `checklist.md` again

## Available Skills

- `javafx-threading` — Task, Service, Platform.runLater, virtual threads
- `kickstartfx-migration` — Gradle, AtlantAFX, fx-values, Ikonli
- `javafx-observable-state` — Properties, bindings, observable collections
- `javafx-layouts` — BorderPane, GridPane, StackPane layouts
- `mvvm-javafx` — Model-View-ViewModel with FXML
- `form-validation` — Input validation, CSS feedback
- `ebean-entity-modeling` — Ebean ORM entities, QBean queries
- `repository-pattern` — Repository interface + Ebean implementation
- `sqlite-desktop` — SQLite WAL, file location, backup
- `junit5-test` — JUnit 5 + real SQLite, no mocks
- `record-value-objects` — Records, sealed classes, pattern matching
- `virtual-thread-service` — Virtual threads, StructuredTaskScope

## Stack

Java 21 LTS · JavaFX 21 · KickStartFX · Maven 3.9+ · Gradle 8+ ·
Ebean 15 · SQLite 3.45+ · AtlantAFX 2.1 · JasperReports 7 ·
ZXing 3.5 · JUnit 5 · TestFX · SLF4J + Logback
