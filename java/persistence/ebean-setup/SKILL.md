---
name: ebean-setup
description: "Use when configuring Ebean ORM for Java 21 + Maven + SQLite."
category: java
tags:
  - java-21
  - persistence
---

# Ebean ORM Setup & Configuration

**Skill ID:** `ebean-setup`  
**Domain:** `persistence`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Ebean in AppBootstrap. DDL from annotations.

---

## Purpose

Use when configuring Ebean ORM for Java 21 + Maven + SQLite.

---

## Concepts Covered

- **Ebean configuration**
- **SQLite platform**
- **DDL generation**
- **Code enhancement**

---

## Rules / Best Practices

1. Configure Ebean programmatically
2. Use SQLitePlatform
3. Code enhancement via annotation processor

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

Ebean in AppBootstrap. DDL from annotations.

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
Implement ebean-setup in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
