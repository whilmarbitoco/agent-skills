---
name: jvm-tuning
description: "Use when optimizing JVM flags for desktop JavaFX apps."
category: java
tags:
  - java-21
  - performance
---

# JVM Tuning for Desktop Apps

**Skill ID:** `jvm-tuning`  
**Domain:** `performance`  
**Level:** advanced  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Flags: Xms256m Xmx512m G1GC Xshare:on.

---

## Purpose

Use when optimizing JVM flags for desktop JavaFX apps.

---

## Concepts Covered

- **G1GC**
- **heap sizing**
- **JIT compilation**

---

## Rules / Best Practices

1. Use G1GC
2. Set Xms to 50% of Xmx
3. Use Xshare:on for CDS

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

Flags: Xms256m Xmx512m G1GC Xshare:on.

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
Implement jvm-tuning in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
