# Agent Skills — Java 21 LTS

A curated library of agent skills for Java 21 LTS development, JavaFX desktop applications, and the Simple POS System project. Designed for AI-assisted development with Hermes/CIEL and compatible agents.

## Structure

```
agent-skills/
├── README.md                          # This file
├── templates/
│   ├── skill-schema.yaml              # Canonical schema for all skills
│   └── SKILL-TEMPLATE.md             # Template for new skills
├── references/
│   ├── canonical-stack.yaml           # Official tech stack baseline
│   ├── taxonomy.yaml                  # Full skill taxonomy (13 domains, 71 skills)
│   ├── coding-standards.md            # Non-negotiable engineering conventions
│   ├── canonical-project-structure.md # Opinionated POS directory layout
├── java/                              # All skill files
│   ├── core-java/                     # 10 skills — language features
│   ├── architecture/                  # 7 skills — desktop architecture
│   ├── ui-javafx/                     # 10 skills — JavaFX engineering
│   ├── ui-kickstartfx/               # 7 skills — KickStartFX conventions
│   ├── persistence/                   # 5 skills — Ebean + SQLite
│   ├── maven/                         # 6 skills — build workflows
│   ├── testing/                       # 3 skills — JUnit 5 + TestFX
│   ├── packaging/                     # 2 skills — jpackage + installers
│   ├── patterns/                      # 6 skills — OOP/SOLID/design patterns
│   ├── performance/                   # 3 skills — JVM tuning, profiling
│   ├── security/                      # 3 skills — secrets, config, auth
│   ├── pos-domain/                    # 7 skills — POS-specific workflows
│   └── reporting/                     # 2 skills — JasperReports + ZXing
├── research/                          # Curated source material
│   ├── java21/                        # OpenJDK JEPs, API docs
│   ├── javafx/                        # OpenJFX documentation
│   ├── maven/                         # Apache Maven reference
│   ├── kickstartfx/                   # KickStartFX docs
│   ├── ebean/                         # Ebean ORM docs
│   ├── sqlite/                        # SQLite documentation
│   ├── jasperreports/                 # JasperReports reference
│   └── zxing/                         # ZXing barcode library
└── scripts/
    ├── ingest-docs.py                 # Fetch and clean official docs
    ├── generate_skills.py             # Batch-generate skill stubs
    └── generate-roadmap.py            # Generate ROADMAP.md from taxonomy
```

## Skill Format

Every skill follows a standardized format:

- **Frontmatter:** name, description, category, tags (YAML)
- **Metadata:** Skill ID, domain, level, version, stack
- **Purpose:** 2-3 sentence summary
- **Concepts Covered:** Bullet list of key concepts
- **Rules / Best Practices:** Numbered, actionable rules
- **Examples:** Java code with correct vs. wrong patterns
- **Anti-Patterns:** What NOT to do, with reasoning
- **Checklists:** Setup, implementation, code review
- **Project-Specific Guidance:** How to apply to Simple POS
- **Recommended Reading:** Tier 1 (official) + Tier 2 (community)
- **AI/Agent Guide:** Conventions, forbidden patterns, prompts, templates

## Canonical Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| Language | Java SE 21 LTS | 21 |
| UI Runtime | JavaFX (OpenJFX) | 21 |
| UI Framework | KickStartFX | 2024.x |
| Build Tool | Apache Maven | 3.9+ |
| ORM | Ebean ORM | 15.x |
| Database | SQLite | 3.45+ |
| Reporting | JasperReports | 7.x |
| Barcode | ZXing | 3.5+ |
| Testing | JUnit 5 + TestFX | 5.11+ |
| Packaging | jpackage | JDK 21 |
| Logging | SLF4J + Logback | 1.5.x |

## Quick Start

### Using a Skill

1. Identify the domain and skill for your task
2. Read the `SKILL.md` file for rules and conventions
3. Follow the checklists during implementation
4. Run the code review checklist before committing

### Creating a New Skill

1. Copy `templates/SKILL-TEMPLATE.md`
2. Place in `java/<domain>/<skill-id>/SKILL.md`
3. Fill all sections following `templates/skill-schema.yaml`
4. Update `references/taxonomy.yaml`
5. Run `python3 scripts/generate-roadmap.py`

## Version Plan

| Version | Scope | Status |
|---------|-------|--------|
| v0.1.0 | Templates + schema + references | ✅ done |
| v0.2.0 | core-java domain (10 skills) | ✅ done |
| v0.3.0 | architecture + patterns domains | ✅ done |
| v0.4.0 | ui-javafx + ui-kickstartfx domains | ✅ done |
| v0.5.0 | persistence + maven + testing domains | ✅ done |
| v0.6.0 | packaging + performance + security domains | ✅ done |
| v0.7.0 | pos-domain + reporting domains | ✅ done |
| v0.8.0 | Reference applications (7 demos) | 🚧 planned |
| v0.9.0 | AI/Agent optimization pass | 🚧 planned |
| v1.0.0 | All skills complete | 🚧 planned |

## Future Expansion

- Spring Boot integration module
- REST sync server
- WebSocket inventory sync
- Multi-branch support
- Hardware integrations (fiscal printers, scales)
- Linux deployment playbooks
- Plugin architecture

## License

MIT License — feel free to use, modify, and distribute.
