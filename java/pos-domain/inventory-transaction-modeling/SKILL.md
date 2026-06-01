---
name: inventory-transaction-modeling
description: "Use when modeling inventory transactions and stock movements."
category: java
tags:
  - java-21
  - pos-domain
---

# Inventory Transaction Modeling

**Skill ID:** `inventory-transaction-modeling`  
**Domain:** `pos-domain`  
**Level:** advanced  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** StockMovement records every qty change.

---

## Purpose

Use when modeling inventory transactions and stock movements.

---

## Concepts Covered

- **Transaction pattern**
- **Stock ledger**
- **Double-entry**

---

## Rules / Best Practices

1. Every stock change is a transaction
2. Immutable transaction records

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

StockMovement records every qty change.

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
Implement inventory-transaction-modeling in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
