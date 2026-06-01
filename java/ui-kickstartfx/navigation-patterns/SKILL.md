---
name: navigation-patterns
description: "Use when implementing KickStartFX navigation between screens."
category: java
tags:
  - java-21
  - ui-kickstartfx
---

# Navigation Patterns

**Skill ID:** `navigation-patterns`  
**Domain:** `ui-kickstartfx`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Sidebar: Dashboard, Inventory, Sales, Reports, Sessions.

---

## Purpose

Use when implementing KickStartFX navigation between screens.

---

## Concepts Covered

- **View registration**
- **View switching**
- **Sidebar nav**

---

## Rules / Best Practices

1. Register all views in workspace config
2. Use workspace.navigateTo()

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

Sidebar: Dashboard, Inventory, Sales, Reports, Sessions.

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
Implement navigation-patterns in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
