---
name: switch-pattern-matching
description: "Use when using pattern matching in switch, instanceof, or record pattern destructuring."
category: java
tags:
  - java-21
  - core-java
---

# Pattern Matching for switch & instanceof

**Skill ID:** `switch-pattern-matching`  
**Domain:** `core-java`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Refactor instanceof chains to pattern matching with sealed PaymentType.

---

## Purpose

Use when using pattern matching in switch, instanceof, or record pattern destructuring.

---

## Concepts Covered

- **instanceof pattern matching**
- **switch with patterns**
- **record patterns**
- **guarded patterns**
- **null handling**

---

## Rules / Best Practices

1. Always use pattern matching not instanceof+cast
2. Combine sealed+switch for exhaustiveness
3. Use guarded patterns for conditions

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

Refactor instanceof chains to pattern matching with sealed PaymentType.

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
Implement switch-pattern-matching in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
