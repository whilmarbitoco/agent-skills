---
name: profiles-environments
description: "Use when managing different build configurations for dev/test/prod."
category: java
tags:
  - java-21
  - maven
---

# Maven Profiles for Environments

**Skill ID:** `profiles-environments`  
**Domain:** `maven`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** dev, test (H2), prod profiles.

---

## Purpose

Use when managing different build configurations for dev/test/prod.

---

## Concepts Covered

- **Maven profiles**
- **Profile activation**
- **Resource filtering**

---

## Rules / Best Practices

1. dev: DDL gen on, debug logging
2. prod: DDL off, warn logging

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

dev, test (H2), prod profiles.

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
Implement profiles-environments in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
