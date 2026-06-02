# Workspace Layout — Checklist

## Implementation

- [ ] Root pom.xml has only `<modules>` and `<dependencyManagement>`
- [ ] `core` module has zero `javafx.*` imports
- [ ] Every module has an explicit `module-info.java`
- [ ] Packages follow `com.pos.<module>.<feature>` convention
- [ ] FXML files are co-located with their controllers in resources

## Review

- [ ] No source code in root module
- [ ] exports clause only exposes intended API packages
- [ ] No circular module dependencies (`requires` graph)
- [ ] Core module builds and tests without JavaFX on the classpath
