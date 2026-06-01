---
name: theme-customization
description: "Use when customizing KickStartFX appearance."
category: java
tags:
  - java-21
  - ui-kickstartfx
---

# Theme Customization

**Skill ID:** `theme-customization`  
**Domain:** `ui-kickstartfx`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Custom blue accent, custom font, dark sidebar.

---

## Purpose

Use when customizing KickStartFX appearance.

---

## Concepts Covered

- **CSS variables**
- **Color scheme**
- **Font configuration**

---

## Rules / Best Practices

1. Use KickStartFX theme API
2. Override CSS variables for branding

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

Custom blue accent, custom font, dark sidebar.

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
Implement theme-customization in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
