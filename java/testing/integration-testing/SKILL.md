---
name: integration-testing
description: "Use when writing integration tests with a real database."
category: java
tags:
  - java-21
  - testing
---

# Integration Testing with SQLite

**Skill ID:** `integration-testing`  
**Domain:** `testing`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Integration tests: full sale flow, stock adjustment.

---

## Purpose

Use when writing integration tests with a real database.

---

## Concepts Covered

- **In-memory SQLite**
- **Schema setup/teardown**
- **Test data seeding**

---

## Rules / Best Practices

1. Use sqlite::memory: for test DB
2. Run schema from Ebean config in setup

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

Integration tests: full sale flow, stock adjustment.

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
Implement integration-testing in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
