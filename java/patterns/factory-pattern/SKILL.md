---
name: factory-pattern
description: "Use when creating objects without specifying exact class."
category: java
tags:
  - java-21
  - patterns
---

# Factory & Abstract Factory

**Skill ID:** `factory-pattern`  
**Domain:** `patterns`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** ReportFactory creates different report types.

---

## Purpose

Use when creating objects without specifying exact class.

---

## Concepts Covered

- **Factory method**
- **Abstract factory**
- **Static factory**

---

## Rules / Best Practices

1. Factory for Report generators
2. Factory for PaymentType handlers

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

ReportFactory creates different report types.

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
Implement factory-pattern in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
