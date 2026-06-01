---
name: observer-pattern
description: "Use when implementing observer pattern with JavaFX properties."
category: java
tags:
  - java-21
  - patterns
---

# Observer Pattern (JavaFX Properties)

**Skill ID:** `observer-pattern`  
**Domain:** `patterns`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Cart observable -> total auto-updates.

---

## Purpose

Use when implementing observer pattern with JavaFX properties.

---

## Concepts Covered

- **Observable**
- **ChangeListener**
- **ObservableList**
- **WeakListener**

---

## Rules / Best Practices

1. JavaFX properties ARE the observer pattern
2. Use weak listeners for memory safety

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

Cart observable -> total auto-updates.

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
Implement observer-pattern in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
