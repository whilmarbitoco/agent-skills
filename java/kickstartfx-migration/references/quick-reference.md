# KickStartFX Quick Reference

| Aspect | API |
|--------|-----|
| Themes | PrimerLight, PrimerDark, NordLight, NordDark, CupertinoLight, CupertinoDark |
| Set theme | `Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet())` |
| Icons | `FontIcon(Material2AL.HOME)`, `FontIcon(MaterialDesign2.MENU)` |
| Icon size | `icon.setIconSize(24)` |
| Reactive Var | `Var<T>` — mutable, read-write |
| Reactive Val | `Val<T>` — derived, read-only |
| Derive | `Val.map(var, fn)` or `val1.combine(val2, fn)` |
| Logging | SLF4J 2.x + JDK Platform Logging (no logback.xml) |
| CSS compile | `Css2Bin` in `processResources` Gradle task |

## Migration from Maven

| Maven | Gradle (KickStartFX) |
|-------|---------------------|
| `spring-boot-starter` | `atlantafx-base` |
| `javafx-controls` | `javafx` script plugin |
| `fontawesomefx` | `ikonli-material2-pack` |
| logback.xml | slf4j-jdk-platform-logging |
| mvn clean build | ./gradlew build |
