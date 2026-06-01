---
name: ebean-queries
description: "Use when writing Ebean queries with the type-safe DSL."
category: java
tags:
  - java-21
  - persistence
---

# Type-Safe Queries with Ebean

**Skill ID:** `ebean-queries`  
**Domain:** `persistence`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** findByName, findLowStock, findToday queries.

---

## Purpose

Use when writing Ebean queries with the type-safe DSL.

---

## Concepts Covered

- **QBean**
- **ExpressionList**
- **fetch joins**
- **pagination**

---

## Rules / Best Practices

1. Use generated Q-beans
2. Use fetch() for eager loading
3. Paginate with setMaxRows()

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

findByName, findLowStock, findToday queries.

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
Implement ebean-queries in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
