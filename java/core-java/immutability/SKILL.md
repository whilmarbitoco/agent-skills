---
name: immutability
description: "Use when designing immutable value objects with records and defensive copying."
category: java
tags:
  - java-21
  - core-java
---

# Immutability Patterns

**Skill ID:** `immutability`  
**Domain:** `core-java`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** All POS value objects as records. ProductView/SaleView for UI.

---

## Purpose

Use when designing immutable value objects with records and defensive copying.

---

## Concepts Covered

- **final fields**
- **records**
- **with-er methods**
- **defensive copying**

---

## Rules / Best Practices

1. Use records for all value objects
2. Never expose mutable internal state
3. Use with-er methods for copy-on-modify

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

All POS value objects as records. ProductView/SaleView for UI.

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
Implement immutability in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
