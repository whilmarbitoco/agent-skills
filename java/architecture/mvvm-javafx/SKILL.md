---
name: mvvm-javafx
description: "Use when implementing Model-View-ViewModel in JavaFX."
category: java
tags:
  - java-21
  - architecture
---

# MVVM Pattern with JavaFX

**Skill ID:** `mvvm-javafx`  
**Domain:** `architecture`  
**Level:** advanced  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Each POS screen has a ViewModel.

---

## Purpose

Use when implementing Model-View-ViewModel in JavaFX.

---

## Concepts Covered

- **ViewModel**
- **Observable state**
- **Data binding**

---

## Rules / Best Practices

1. ViewModel exposes ObservableList/Property for binding
2. ViewModel has no reference to View

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

Each POS screen has a ViewModel.

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
Implement mvvm-javafx in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
