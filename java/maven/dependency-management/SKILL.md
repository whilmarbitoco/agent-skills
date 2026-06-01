---
name: dependency-management
description: "Use when managing Maven dependencies and version conflicts."
category: java
tags:
  - java-21
  - maven
---

# Dependency Management & BOMs

**Skill ID:** `dependency-management`  
**Domain:** `maven`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Parent POM imports JavaFX and Ebean BOMs.

---

## Purpose

Use when managing Maven dependencies and version conflicts.

---

## Concepts Covered

- **dependencyManagement**
- **BOM**
- **Version conflicts**
- **Exclusions**

---

## Rules / Best Practices

1. Define all versions in parent POM
2. Import BOMs for JavaFX and Ebean
3. Never use LATEST or RELEASE

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

Parent POM imports JavaFX and Ebean BOMs.

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
Implement dependency-management in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
