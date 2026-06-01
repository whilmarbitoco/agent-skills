---
name: barcode-scanning-flow
description: "Use when implementing barcode scanning integration."
category: java
tags:
  - java-21
  - pos-domain
---

# Barcode Scanning Flow

**Skill ID:** `barcode-scanning-flow`  
**Domain:** `pos-domain`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** USB scanner in keyboard wedge mode.

---

## Purpose

Use when implementing barcode scanning integration.

---

## Concepts Covered

- **ZXing**
- **Camera capture**
- **External scanner**
- **Keyboard wedge**

---

## Rules / Best Practices

1. Support camera and USB scanner
2. Debounce rapid scan events

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

USB scanner in keyboard wedge mode.

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
Implement barcode-scanning-flow in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
