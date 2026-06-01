---
name: reproducible-builds
description: "Use when ensuring Maven builds are reproducible."
category: java
tags:
  - java-21
  - maven
---

# Reproducible Builds

**Skill ID:** `reproducible-builds`  
**Domain:** `maven`  
**Level:** beginner  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** mvnw in repo. All plugin versions pinned.

---

## Purpose

Use when ensuring Maven builds are reproducible.

---

## Concepts Covered

- **Maven wrapper**
- **Plugin versions**
- **Dependency locking**

---

## Rules / Best Practices

1. Maven Wrapper required
2. Pin all plugin versions
3. mvnw in repo

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

mvnw in repo. All plugin versions pinned.

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
Implement reproducible-builds in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
