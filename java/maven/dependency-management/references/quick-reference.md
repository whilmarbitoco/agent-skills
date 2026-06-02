# Dependency Management — Quick Reference

| Scope | Transitive? | Available in | Ships in jar? |
|---|---|---|---|
| `compile` | Yes | compile, test, runtime | Yes |
| `provided` | No | compile, test | No |
| `runtime` | Yes | runtime, test | Yes |
| `test` | No | test only | No |
| `import` | N/A | management only | N/A |

| Problem | Command |
|---|---|
| See resolved tree | `mvn dependency:tree` |
| Filter tree | `mvn dependency:tree -Dincludes=groupId` |
| Find conflicts | `mvn dependency:tree -Dverbose` |
| Analyze unused | `mvn dependency:analyze` |
| Enforce convergence | `mvn enforcer:enforce` |

| Rule | Detail |
|---|---|
| Nearest wins | Closest to root in tree wins |
| First declared wins | Same depth → first declared wins |
| BOM import | Must be in `<dependencyManagement>` |
