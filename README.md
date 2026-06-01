# Agent Skills — Java 21 LTS

Cross-agent compatible skills for Java 21 + JavaFX + KickStartFX development.
Works with any agent that supports the [Agent Skills](https://agentskills.io) format
(Claude Code, Cursor, Aider, Codex, OpenCode, Hermes, and others).

## Install

### For CLI Users

```bash
git clone https://github.com/whilmarbitoco/agent-skills.git
cd agent-skills

# Install to current project (creates .agents/skills/)
./scripts/install.sh

# Install globally for all projects
./scripts/install.sh --global

# Install for Cursor editor
./scripts/install.sh --cursor

# Install for Claude Code
./scripts/install.sh --claude

# List available skills
./scripts/install.sh --list
```

### For AI Agents

Read `AGENTS.md` for the full usage guide. Quick version:

1. Each skill is a directory under `java/` with a `SKILL.md`
2. The `description` frontmatter field tells you when the skill applies
3. Load `SKILL.md` first, then `examples/`, `anti-patterns.md`, `checklist.md`, `references/` as needed
4. See `java/javafx-threading/` for a reference example

## Structure

Each skill is a directory with:

```
skill-name/
├── SKILL.md              # Compact: concepts, rules, anti-patterns (< 35 lines)
├── examples/             # Real Java code — copy-paste ready
├── anti-patterns.md      # What NOT to do, with code
├── checklist.md          # Implementation + review checklists
└── references/           # Quick reference tables
```

## Skills

| Skill | What It Teaches | Examples |
|-------|----------------|----------|
| `javafx-threading` | Task, Service, Platform.runLater | 3 |
| `virtual-thread-service` | Virtual threads, StructuredTaskScope | 3 |
| `kickstartfx-migration` | Gradle, AtlantAFX, fx-values, Ikonli | 3 |
| `javafx-observable-state` | Properties, bindings, ObservableList | 3 |
| `javafx-layouts` | BorderPane, GridPane, VBox/HBox | 3 |
| `mvvm-javafx` | Model-View-ViewModel with FXML | 3 |
| `form-validation` | Input validation, CSS feedback | 2 |
| `ebean-entity-modeling` | Ebean entities, QBean queries | 3 |
| `repository-pattern` | Repository interface + Ebean impl | 1 |
| `sqlite-desktop` | SQLite WAL, file location, backup | 1 |
| `junit5-test` | JUnit 5 + real SQLite, no mocks | 1 |
| `record-value-objects` | Records, sealed classes, pattern matching | 1 |

## Stack

Java 21 LTS · JavaFX 21 · KickStartFX · Maven 3.9+ · Gradle 8+ · Ebean 15 ·
SQLite 3.45+ · AtlantAFX 2.1 · JasperReports 7 · ZXing 3.5 · JUnit 5 ·
TestFX · SLF4J + Logback

## License

Apache 2.0
