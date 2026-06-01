---
name: reusable-components
description: "Use when creating custom JavaFX controls or composite components."
category: java
tags:
  - java-21
  - ui-javafx
---

# Reusable UI Components

**Skill ID:** `reusable-components`  
**Domain:** `ui-javafx`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** ProductCard, SaleLineItem, MoneyField as reusable components.

---

## Purpose

Use when creating custom JavaFX controls or composite components.

---

## Concepts Covered

- **Custom control**
- **fxml:include**
- **ControlsFX**

---

## Rules / Best Practices

1. Prefer FXML-included components
2. Use ControlsFX for advanced controls

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

ProductCard, SaleLineItem, MoneyField as reusable components.

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
Implement reusable-components in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
