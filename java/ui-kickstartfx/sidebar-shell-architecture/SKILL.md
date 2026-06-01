---
name: sidebar-shell-architecture
description: "Use when implementing the main shell with sidebar navigation."
category: java
tags:
  - java-21
  - ui-kickstartfx
---

# Sidebar Shell Architecture

**Skill ID:** `sidebar-shell-architecture`  
**Domain:** `ui-kickstartfx`  
**Level:** advanced  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** 240px sidebar with icons+labels. Content area views.

---

## Purpose

Use when implementing the main shell with sidebar navigation.

---

## Concepts Covered

- **Sidebar layout**
- **Content area**
- **Responsive behavior**

---

## Rules / Best Practices

1. Sidebar fixed width, content fills remaining space
2. Collapse on small screens

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

240px sidebar with icons+labels. Content area views.

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
Implement sidebar-shell-architecture in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
