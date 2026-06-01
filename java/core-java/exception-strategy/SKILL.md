---
name: exception-strategy
description: "Use when designing exception hierarchies for domain errors."
category: java
tags:
  - java-21
  - core-java
---

# Exception Handling Strategy

**Skill ID:** `exception-strategy`  
**Domain:** `core-java`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Create InsufficientStockException, InvalidReceiptException, CashSessionClosedException.

---

## Purpose

Use when designing exception hierarchies for domain errors.

---

## Concepts Covered

- **Domain exceptions**
- **Exception hierarchy**
- **Optional vs exceptions**
- **Error messages**

---

## Rules / Best Practices

1. Use domain-specific unchecked exceptions
2. Include entity context in messages
3. Use Optional for nullable query results

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

Create InsufficientStockException, InvalidReceiptException, CashSessionClosedException.

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
Implement exception-strategy in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
