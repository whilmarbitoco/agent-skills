---
name: feature-based-packaging
description: "Use when deciding between package-by-layer vs package-by-feature."
category: java
tags:
  - java-21
  - architecture
---

# Feature-Based Package Organization

**Skill ID:** `feature-based-packaging`  
**Domain:** `architecture`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Features: inventory, sales, reports, sessions.

---

## Purpose

Use when deciding between package-by-layer vs package-by-feature.

---

## Concepts Covered

- **Feature modules**
- **Package cohesion**
- **Navigation boundaries**

---

## Rules / Best Practices

1. Group by feature first
2. Each feature has own UI+service+repository

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

Features: inventory, sales, reports, sessions.

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
Implement feature-based-packaging in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
