---
name: domain-driven-structure-lite
description: "Use when applying DDD concepts to JavaFX desktop apps."
category: java
tags:
  - java-21
  - architecture
---

# Domain-Driven Structure (Lite)

**Skill ID:** `domain-driven-structure-lite`  
**Domain:** `architecture`  
**Level:** advanced  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Sale and Product are aggregate roots.

---

## Purpose

Use when applying DDD concepts to JavaFX desktop apps.

---

## Concepts Covered

- **Entities**
- **Value objects**
- **Aggregates**
- **Domain events**

---

## Rules / Best Practices

1. Value objects are records
2. Repositories only for aggregates

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

Sale and Product are aggregate roots.

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
Implement domain-driven-structure-lite in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
