---
name: dependency-injection-pattern
description: "Use when implementing constructor-based DI."
category: java
tags:
  - java-21
  - patterns
---

# Dependency Injection Pattern

**Skill ID:** `dependency-injection-pattern`  
**Domain:** `patterns`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** AppBootstrap wires repositories -> services -> controllers.

---

## Purpose

Use when implementing constructor-based DI.

---

## Concepts Covered

- **Constructor injection**
- **Composition root**
- **Manual wiring**

---

## Rules / Best Practices

1. All deps via constructor
2. Composition root in AppBootstrap
3. No ServiceLocator

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

AppBootstrap wires repositories -> services -> controllers.

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
Implement dependency-injection-pattern in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
