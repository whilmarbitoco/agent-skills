---
name: memory-profiling
description: "Use when diagnosing memory leaks in JavaFX apps."
category: java
tags:
  - java-21
  - performance
---

# Memory Profiling & Leak Detection

**Skill ID:** `memory-profiling`  
**Domain:** `performance`  
**Level:** advanced  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Common leak: binding not removed on screen close.

---

## Purpose

Use when diagnosing memory leaks in JavaFX apps.

---

## Concepts Covered

- **Heap dump**
- **VisualVM**
- **JavaFX listener leaks**

---

## Rules / Best Practices

1. Check for listener leaks
2. Use WeakListener
3. Monitor with VisualVM

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

Common leak: binding not removed on screen close.

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
Implement memory-profiling in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
