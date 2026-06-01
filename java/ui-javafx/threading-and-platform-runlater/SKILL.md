---
name: threading-and-platform-runlater
description: "Use when working with JavaFX threading and background tasks."
category: java
tags:
  - java-21
  - ui-javafx
---

# Threading & Platform.runLater

**Skill ID:** `threading-and-platform-runlater`  
**Domain:** `ui-javafx`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** All DB queries on virtual thread. Results via Platform.runLater.

---

## Purpose

Use when working with JavaFX threading and background tasks.

---

## Concepts Covered

- **FX Application Thread**
- **Platform.runLater()**
- **Task**
- **Service**

---

## Rules / Best Practices

1. Never block FX thread with I/O
2. Use Task for cancellable background work
3. Use Service for restartable work

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

All DB queries on virtual thread. Results via Platform.runLater.

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
Implement threading-and-platform-runlater in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
