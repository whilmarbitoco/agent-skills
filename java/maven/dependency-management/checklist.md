# Dependency Management — Checklist

## Implementation

- [ ] All third-party versions in parent `<dependencyManagement>`
- [ ] BOMs imported with `<scope>import</scope>` before other deps
- [ ] No `<version>` in child POM dependencies
- [ ] Test-only deps use `<scope>test</scope>`
- [ ] Unwanted transitive deps have `<exclusions>`
- [ ] `maven-enforcer-plugin` with `<dependencyConvergence>` enabled
- [ ] Plugin versions pinned in `<pluginManagement>`

## Review

- [ ] `mvn dependency:tree` shows expected versions
- [ ] No `FAILED` from enforcer dependencyConvergence rule
- [ ] No duplicate jars in `mvn dependency:analyze-duplicate`
- [ ] `mvn dependency:analyze` reports no used undeclared deps
