# Workspace Layout — Quick Reference

| Concept | Convention |
|---|---|
| Root module | pom.xml with `<modules>` only, no source |
| Core module | Zero `javafx.*` imports, domain logic only |
| UI module | FXML + controllers, depends on core |
| Package layout | `com.pos.<module>.<feature>` |
| Module descriptor | Explicit `module-info.java` per module |
| FXML location | Mirrors controller package under resources |
| Shared enums | Exported from `core`, used by `ui` for binding |
