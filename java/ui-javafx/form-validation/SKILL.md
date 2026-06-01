---
name: form-validation
description: "Use when implementing form input validation in JavaFX."
category: java
tags:
  - java-21
  - ui-javafx
---

# Form Validation Patterns

**Skill ID:** `form-validation`  
**Domain:** `ui-javafx`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Product form: name required, price > 0, stock >= 0.

---

## Purpose

Use when implementing form input validation in JavaFX.

---

## Concepts Covered

- **Validation**
- **Visual feedback**
- **Error messages**

---

## Rules / Best Practices

1. Validate on focus lost AND on submit
2. Use CSS :invalid pseudo-class
3. Disable submit until valid

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

Product form: name required, price > 0, stock >= 0.

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
Implement form-validation in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
