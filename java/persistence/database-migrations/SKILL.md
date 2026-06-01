---
name: database-migrations
description: "Use when managing database schema changes across app versions."
category: java
tags:
  - java-21
  - persistence
---

# Database Migrations

**Skill ID:** `database-migrations`  
**Domain:** `persistence`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Migrations in resources/db/migration/.

---

## Purpose

Use when managing database schema changes across app versions.

---

## Concepts Covered

- **Ebean migration**
- **DDL diff**
- **Migration scripts**

---

## Rules / Best Practices

1. Use Ebean DDL diff for generating migrations
2. Number scripts sequentially

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

Migrations in resources/db/migration/.

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
Implement database-migrations in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
