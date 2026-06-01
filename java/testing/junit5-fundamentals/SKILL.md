---
name: junit5-fundamentals
description: "Use when writing unit tests with JUnit 5."
category: java
tags:
  - java-21
  - testing
---

# JUnit 5 Fundamentals

**Skill ID:** `junit5-fundamentals`  
**Domain:** `testing`  
**Level:** beginner  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** All service logic tested with JUnit 5.

---

## Purpose

Use when writing unit tests with JUnit 5.

---

## Concepts Covered

- **Test annotation**
- **BeforeEach**
- **DisplayName**
- **Assertions**
- **Parameterized tests**

---

## Rules / Best Practices

1. Use DisplayName for readable names
2. One assertion per test
3. Use assertAll for multiple

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

All service logic tested with JUnit 5.

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
Implement junit5-fundamentals in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
