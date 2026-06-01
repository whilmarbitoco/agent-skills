---
name: sqlite-best-practices
description: "Use when working with SQLite in a desktop application."
category: java
tags:
  - java-21
  - persistence
---

# SQLite Best Practices for Desktop Apps

**Skill ID:** `sqlite-best-practices`  
**Domain:** `persistence`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** DB in app data dir. WAL mode. Daily backup.

---

## Purpose

Use when working with SQLite in a desktop application.

---

## Concepts Covered

- **WAL mode**
- **File location**
- **Backup**
- **Thread safety**

---

## Rules / Best Practices

1. Enable WAL mode
2. Store DB in user home directory
3. Implement periodic backup

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

DB in app data dir. WAL mode. Daily backup.

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
Implement sqlite-best-practices in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
