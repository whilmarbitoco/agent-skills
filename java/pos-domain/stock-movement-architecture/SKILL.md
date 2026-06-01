---
name: stock-movement-architecture
description: "Use when designing the stock movement system."
category: java
tags:
  - java-21
  - pos-domain
---

# Stock Movement Architecture

**Skill ID:** `stock-movement-architecture`  
**Domain:** `pos-domain`  
**Level:** advanced  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Purchase, Sale, Adjustment, Return, Loss.

---

## Purpose

Use when designing the stock movement system.

---

## Concepts Covered

- **Movement types**
- **Approval workflow**
- **Thresholds**
- **Reorder alerts**

---

## Rules / Best Practices

1. 5 movement types
2. Adjustments require reason
3. Low-stock threshold

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

Purchase, Sale, Adjustment, Return, Loss.

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
Implement stock-movement-architecture in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
