---
name: string-templates
description: "Use when working with STR. string interpolation (JEP 430 preview)."
category: java
tags:
  - java-21
  - core-java
---

# String Templates (Preview)

**Skill ID:** `string-templates`  
**Domain:** `core-java`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** STR. for receipt line formatting and log messages.

---

## Purpose

Use when working with STR. string interpolation (JEP 430 preview).

---

## Concepts Covered

- **STR. processor**
- **String interpolation**
- **FMT. processor**

---

## Rules / Best Practices

1. Use STR. for safe interpolation
2. Use FMT. for formatted output

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

STR. for receipt line formatting and log messages.

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
Implement string-templates in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
