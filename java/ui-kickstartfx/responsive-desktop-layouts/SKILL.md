---
name: responsive-desktop-layouts
description: "Use when making JavaFX desktop layouts responsive."
category: java
tags:
  - java-21
  - ui-kickstartfx
---

# Responsive Desktop Layouts

**Skill ID:** `responsive-desktop-layouts`  
**Domain:** `ui-kickstartfx`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Min 1024x768. Sidebar collapses below 900px.

---

## Purpose

Use when making JavaFX desktop layouts responsive.

---

## Concepts Covered

- **Binding to scene width**
- **Min-max constraints**
- **Breakpoints**

---

## Rules / Best Practices

1. Use binding not listeners
2. Set min/max sizes on regions

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

Min 1024x768. Sidebar collapses below 900px.

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
Implement responsive-desktop-layouts in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
