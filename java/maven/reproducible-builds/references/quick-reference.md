# Reproducible Builds — Quick Reference

| Property | Purpose |
|---|---|
| `project.build.outputTimestamp` | Normalize all entry timestamps |
| `maven.compiler.release` | Pin Java version (not `source`/`target`) |
| `sorted` (assembly plugin) | Deterministic file ordering |

| Flag | Purpose |
|---|---|
| `--batch-mode` | No interactive output, deterministic logging |
| `--strict-checksums` | Fail on corrupted mirrors |
| `--fail-at-end` | Show all failures per-module |
| `-nsu` | Disable SNAPSHOT updates during build |

| Goal | Command |
|---|---|
| Verify artifact | `mvn artifact:compare` |
| Strip timestamps | `strip-nondeterminism -t jar target/*.jar` |
| Generate buildinfo | `mvn artifact:buildinfo` |
| SCM timestamp | `git log --format=%aI -1 <tag>` |
