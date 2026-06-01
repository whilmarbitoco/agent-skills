---
name: offline-first-design
description: "Use when designing the POS to work without network connectivity."
category: java
tags:
  - java-21
  - architecture
---

# Offline-First Design

**Skill ID:** `offline-first-design`  
**Domain:** `architecture`  
**Level:** advanced  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Simple POS works fully offline.

---

## Purpose

Use when designing the POS to work without network connectivity.

---

## Concepts Covered

- **Local database**
- **Transaction queue**
- **Sync strategies**

---

## Rules / Best Practices

1. All data stored locally in SQLite
2. Never block UI waiting for network

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

Simple POS works fully offline.

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
Implement offline-first-design in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
