---
name: testfx-ui-testing
description: "Use when testing JavaFX UI with TestFX."
category: java
tags:
  - java-21
  - testing
---

# TestFX for JavaFX UI Testing

**Skill ID:** `testfx-ui-testing`  
**Domain:** `testing`  
**Level:** advanced  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** UI tests: product form, sale flow, search filtering.

---

## Purpose

Use when testing JavaFX UI with TestFX.

---

## Concepts Covered

- **TestFX**
- **FXRobot**
- **headless testing**
- **Monocle**

---

## Rules / Best Practices

1. Run UI tests headless with Monocle
2. Use robot to simulate user actions

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

UI tests: product form, sale flow, search filtering.

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
Implement testfx-ui-testing in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
