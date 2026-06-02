# Reproduducible Builds — Checklist

## Implementation

- [ ] `<project.build.outputTimestamp>` set in parent POM
- [ ] Every plugin version pinned in `<pluginManagement>`
- [ ] No `LATEST` or `RELEASE` dependency versions
- [ ] `.mvn/maven.config` committed with deterministic flags
- [ ] `--batch-mode` used in CI
- [ ] `--strict-checksums` in CI to reject corrupted downloads
- [ ] `buildinfo` file generated in release pipeline

## Review

- [ ] Two identical source commits produce byte-identical artifacts
- [ ] `mvn artifact:compare` passes against reference build
- [ ] `strip-nondeterminism` reports no differences between builds
- [ ] `git log --format=%aI -1 v1.0.0` matches outputTimestamp
