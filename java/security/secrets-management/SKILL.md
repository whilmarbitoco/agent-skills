---
name: secrets-management
description: >
  Extends agent's knowledge of storing and accessing secrets securely in a Java POS
  application. Use when handling API keys, database passwords, or encryption keys.
compatibility: Java 21+
metadata:
  domain: security
  level: intermediate
  stack: [java-21, slf4j-2]
  version: "1.0.0"
---

# Secrets Management

POS applications handle payment credentials, DB passwords, and API keys.
Hardcoding or storing them in plain config files is a critical vulnerability.
This skill covers in-memory handling and externalized secret stores.

## Concepts

- **SecretStore** abstraction — in-memory map for dev, env vars or vault for prod
- **char[] over String** — Strings are immutable and linger in the intern pool; char[] can be zeroed
- **Short-lived tokens** — fetch once, use, discard — never cache credentials in static fields
- **Environment variables** — inject secrets at runtime, never commit to VCS

## Rules

1. Never hardcode credentials in source code or properties files checked into VCS.
2. Store secrets in environment variables or a secret manager; read at startup.
3. Use `char[]` for password fields so memory can be zeroed after use.
4. Use a `SecretStore` interface with pluggable backend (env-var / file / vault).
5. Log only the presence/absence of a secret, never its value.
6. Zero out `char[]` arrays immediately after use with `Arrays.fill`.

## Anti-patterns

See [anti-patterns.md](./anti-patterns.md).

## Related

- config-encrypted — encrypting configuration at rest