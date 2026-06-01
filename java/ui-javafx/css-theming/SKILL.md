---
name: css-theming
description: "Use when styling JavaFX applications with CSS."
category: java
tags:
  - java-21
  - ui-javafx
---

# JavaFX CSS Theming

**Skill ID:** `css-theming`  
**Domain:** `ui-javafx`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Custom CSS with looked-up colors. Dark mode toggles CSS class.

---

## Purpose

Use when styling JavaFX applications with CSS.

---

## Concepts Covered

- **CSS selectors**
- **Looked-up colors**
- **Dark mode**
- **Modena theme**

---

## Rules / Best Practices

1. Use looked-up colors for theme variables
2. Separate CSS files per theme

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

Custom CSS with looked-up colors. Dark mode toggles CSS class.

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
Implement css-theming in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
