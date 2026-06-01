---
name: layered-architecture
description: "Use when structuring a JavaFX desktop app with clear separation of concerns."
category: java
tags:
  - java-21
  - architecture
---

# Layered Architecture for Desktop Apps

**Skill ID:** `layered-architecture`  
**Domain:** `architecture`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Simple POS: domain -> persistence -> services -> ui.

---

## Purpose

Use when structuring a JavaFX desktop app with clear separation of concerns.

---

## Concepts Covered

- **Layer boundaries**
- **Dependency direction**
- **Package structure**

---

## Rules / Best Practices

1. Domain has zero dependencies on other layers
2. UI depends on service interfaces only
3. No cyclic dependencies

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

Simple POS: domain -> persistence -> services -> ui.

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
Implement layered-architecture in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
