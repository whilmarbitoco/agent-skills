---
name: event-driven-ui
description: "Use when implementing event-based communication between UI and services."
category: java
tags:
  - java-21
  - architecture
---

# Event-Driven UI Architecture

**Skill ID:** `event-driven-ui`  
**Domain:** `architecture`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Cart updated -> total recalculated. Sale completed -> stock updated.

---

## Purpose

Use when implementing event-based communication between UI and services.

---

## Concepts Covered

- **Event bus**
- **Publish-subscribe**
- **JavaFX events**

---

## Rules / Best Practices

1. Use JavaFX events for UI events
2. Lightweight event bus for service-to-service

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

Cart updated -> total recalculated. Sale completed -> stock updated.

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
Implement event-driven-ui in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
