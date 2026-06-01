---
name: workspace-layout
description: "Use when setting up KickStartFX workspace structure."
category: java
tags:
  - java-21
  - ui-kickstartfx
---

# KickStartFX Workspace Layout

**Skill ID:** `workspace-layout`  
**Domain:** `ui-kickstartfx`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Simple POS uses single workspace with sidebar navigation.

---

## Purpose

Use when setting up KickStartFX workspace structure.

---

## Concepts Covered

- **Workspace**
- **Window configuration**

---

## Rules / Best Practices

1. Extend KickStartFX Application class
2. Configure workspace in start method

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

Simple POS uses single workspace with sidebar navigation.

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
Implement workspace-layout in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
