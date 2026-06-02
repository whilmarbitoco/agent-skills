# Multi-Module Projects — Quick Reference

| Element | Location | Purpose |
|---|---|---|
| `<packaging>pom</packaging>` | Parent POM only | Marks reactor root |
| `<modules>` | Parent POM | Lists child modules in build order |
| `<dependencyManagement>` | Parent POM | Single source of truth for versions |
| `<pluginManagement>` | Parent POM | Plugin config reused by children |
| `${project.version}` | Any POM | Reactor-resolved version |
| `<relativePath/>` | Child `<parent>` | Skips filesystem lookup |

| Goal | Command |
|---|---|
| Build all | `mvn -T 1C clean install` |
| Build single module + deps | `mvn -pl desktop-app -am install` |
| Skip verification modules | `mvn install -pl !integration-tests` |
| Verify no cycles | `mvn dependency:analyze` |
| Force parent version | `mvn versions:set -DnewVersion=1.1.0` |
