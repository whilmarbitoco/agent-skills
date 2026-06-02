---
name: reproducible-builds
description: >
  Extends agent's knowledge of creating bit-for-bit reproducible Maven
  builds. Use when building release artifacts that must be verifiable
  or when enabling deterministic CI/CD pipelines.
compatibility: Java 21+
metadata:
  domain: maven
  advanced: advanced
  stack: [java-21, maven-3.9]
  version: "1.0.0"
---

# Reproducible Builds

A reproducible build produces the identical artifact (byte-for-byte)
given the same source, regardless of when or where it's built. This is
essential for supply-chain security and CI caching.

## Concepts

- **Timestamps** — jars embed `META-INF/MANIFEST.MF` and file timestamps
  that differ per build.
- **`project.build.outputTimestamp`** — Maven property that normalizes
  all timestamps in the output artifact.
- **Sorted entries** — reproducible-order file listing in jars/zips.
- **`.mvn/maven.config`** — reproducible CLI flags across environments.

## Rules

1. Set `<project.build.outputTimestamp>` to the SCM tag timestamp (as
   seconds since epoch).
2. Configure `maven-jar-plugin`, `maven-source-plugin`, etc. to inherit
   this timestamp.
3. Sort file entries: `<sorted>true</sorted>` in plugin configs where
   available.
4. Pin all plugin versions explicitly — never use `LATEST` or `RELEASE`.
5. Use `--batch-mode` in CI to avoid interactive output affecting logs.
6. Verify reproducibility with `mvn artifact:compare` or `reproducible-builds.org`
   `strip-nondeterminism` tool.
7. Generate a `buildinfo` file capturing exact dependency graph +
   environment for auditing.

## Anti-patterns

See [anti-patterns.md](./anti-patterns.md).

## Related

- dependency-management — pinned versions
- shading-and-packaging — deterministic uber-jar output
- profiles-environments — environment-agnostic builds
