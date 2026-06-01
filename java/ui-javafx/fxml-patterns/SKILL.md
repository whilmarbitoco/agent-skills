---
name: fxml-patterns
description: "Use when working with FXML files and controller injection."
category: java
tags:
  - java-21
  - ui-javafx
---

# FXML & Controller Patterns

**Skill ID:** `fxml-patterns`  
**Domain:** `ui-javafx`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Each POS screen is FXML with dedicated controller via factory.

---

## Purpose

Use when working with FXML files and controller injection.

---

## Concepts Covered

- **fx:controller**
- **fx:id**
- **fx:include**
- **Controller factory**

---

## Rules / Best Practices

1. One FXML per screen
2. Controller receives dependencies via factory
3. Keep controllers thin

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

Each POS screen is FXML with dedicated controller via factory.

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
Implement fxml-patterns in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
