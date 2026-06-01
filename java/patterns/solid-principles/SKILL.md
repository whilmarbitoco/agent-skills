---
name: solid-principles
description: "Use when evaluating code against SOLID design principles."
category: java
tags:
  - java-21
  - patterns
---

# SOLID Principles in Java

**Skill ID:** `solid-principles`  
**Domain:** `patterns`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Each POS service has one responsibility.

---

## Purpose

Use when evaluating code against SOLID design principles.

---

## Concepts Covered

- **SRP**
- **OCP**
- **LSP**
- **ISP**
- **DIP**

---

## Rules / Best Practices

1. One reason to change per class
2. Extend not modify
3. Small focused interfaces

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

Each POS service has one responsibility.

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
Implement solid-principles in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
