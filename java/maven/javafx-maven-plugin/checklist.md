# JavaFX Maven Plugin — Checklist

## Implementation

- [ ] `javafx-bom` imported in parent `<dependencyManagement>`
- [ ] Each JavaFX module declared explicitly (no wildcards)
- [ ] `javafx-maven-plugin` configured with `mainClass`
- [ ] `mainClass` uses `module/Class` format for modular projects
- [ ] `module-info.java` lists all `requires` for JavaFX modules
- [ ] `mvn javafx:run` starts the app on dev machine
- [ ] Platform classifiers configured for target OS

## Review

- [ ] `mvn javafx:jlink` produces a runnable runtime image
- [ ] `mvn -Pprod javafx:jlink` on CI creates distribution image
- [ ] No `ClassNotFoundException` for JavaFX native libraries
- [ ] Module path (not classpath) used when `module-info.java` exists
