# Multi-Module Projects — Checklist

## Implementation

- [ ] Parent POM has `<packaging>pom</packaging>`
- [ ] All module versions declared in parent `<dependencyManagement>`
- [ ] All plugin configs in parent `<pluginManagement>`
- [ ] No `<version>` in child inter-module dependencies
- [ ] Modules listed in dependency order (verify with `mvn dependency:tree`)
- [ ] Each module has a single responsibility
- [ ] No circular dependencies (`mvn dependency:analyze` will warn)
- [ ] `<relativePath/>` in child `<parent>` element

## Review

- [ ] Reactor builds with `mvn -pl desktop-app -am` without errors
- [ ] `mvn -pl integration-tests -am` skips ITs unless `-Pintegration`
- [ ] Version change in parent propagates to all children on next build
- [ ] No duplicate dependency declarations across modules
