---
name: streams-vs-loops
description: "Use when deciding between Stream API and imperative loops."
category: java
tags:
  - java-21
  - core-java
---

# Streams API vs Imperative Loops

**Skill ID:** `streams-vs-loops`  
**Domain:** `core-java`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Streams for report data transformation. Loops for I/O.

---

## Purpose

Use when deciding between Stream API and imperative loops.

---

## Concepts Covered

- **Stream pipeline**
- **filter-map-reduce**
- **collectors**
- **parallel streams caution**

---

## Rules / Best Practices

1. Use streams for multi-step transformations
2. Use loops for side effects
3. Never use parallel streams on small collections

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

Streams for report data transformation. Loops for I/O.

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
Implement streams-vs-loops in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
