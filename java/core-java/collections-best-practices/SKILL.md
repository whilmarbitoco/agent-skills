---
name: collections-best-practices
description: "Use when choosing collection types or creating immutable collections in Java 21."
category: java
tags:
  - java-21
  - core-java
---

# Collections & Immutable Data

**Skill ID:** `collections-best-practices`  
**Domain:** `core-java`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Immutable collections for reference data; copyOf in service getters.

---

## Purpose

Use when choosing collection types or creating immutable collections in Java 21.

---

## Concepts Covered

- **Immutable collections**
- **List.of()**
- **Map.of()**
- **copyOf()**

---

## Rules / Best Practices

1. Use List.of for static data
2. Use List.copyOf for defensive copies
3. Never return mutable internal collections

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

Immutable collections for reference data; copyOf in service getters.

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
Implement collections-best-practices in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
