---
name: config-encryption
description: "Use when storing sensitive configuration on disk."
category: java
tags:
  - java-21
  - security
---

# Configuration Encryption

**Skill ID:** `config-encryption`  
**Domain:** `security`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** DB password stored encrypted.

---

## Purpose

Use when storing sensitive configuration on disk.

---

## Concepts Covered

- **AES encryption**
- **Key derivation**

---

## Rules / Best Practices

1. Encrypt sensitive config at rest
2. Use AES-256-GCM

---

## Checklists

### Implementation
- [ ] Follow all rules above
- [ ] Java 21 features used where applicable
- [ ] POS domain guidance followed

### Code Review
- [ ] No layer boundary violations
- [ ] Constructor injection used

---

## Project-Specific Guidance (Simple POS)

DB password stored encrypted.

---

## Recommended Reading
- [Java 21 Docs](https://docs.oracle.com/en/java/javase/21/)  
- [OpenJDK JEPs](https://openjdk.org/projects/jdk/21/)

---

## AI/Agent Guide

### Strict Conventions
- Follow all rules above
- Java 21 features (records, sealed, virtual threads, pattern matching)
- Constructor injection only; no static mutable state

### Preferred Libraries
- See references/canonical-stack.yaml

### Example Prompts

```
Implement config-encryption in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
