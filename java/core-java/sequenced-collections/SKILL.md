---
name: sequenced-collections
description: "Use when working with SequencedCollection, SequencedSet, SequencedMap (JEP 431)."
category: java
tags:
  - java-21
  - core-java
---

# Sequenced Collections API

**Skill ID:** `sequenced-collections`  
**Domain:** `core-java`  
**Level:** beginner  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Replace get(0)/get(size-1) in POS list views.

---

## Purpose

Use when working with SequencedCollection, SequencedSet, SequencedMap (JEP 431).

---

## Concepts Covered

- **SequencedCollection**
- **reversed()**
- **getFirst()**
- **getLast()**

---

## Rules / Best Practices

1. Use reversed() for backward iteration
2. Prefer getFirst/getLast over get(0)/get(size-1)

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

Replace get(0)/get(size-1) in POS list views.

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
Implement sequenced-collections in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
