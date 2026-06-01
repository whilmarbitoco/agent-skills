---
name: multi-module-projects
description: "Use when structuring a Maven project with multiple modules."
category: java
tags:
  - java-21
  - maven
---

# Multi-Module Maven Projects

**Skill ID:** `multi-module-projects`  
**Domain:** `maven`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Modules: core, persistence, ui, app.

---

## Purpose

Use when structuring a Maven project with multiple modules.

---

## Concepts Covered

- **Parent POM**
- **Module BOM**
- **Dependency management**

---

## Rules / Best Practices

1. Parent POM defines versions in dependencyManagement
2. Each module has own pom.xml

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

Modules: core, persistence, ui, app.

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
Implement multi-module-projects in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
