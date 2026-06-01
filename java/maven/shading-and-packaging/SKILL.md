---
name: shading-and-packaging
description: "Use when building a distributable JAR or native installer."
category: java
tags:
  - java-21
  - maven
---

# Shading, Packaging & jpackage

**Skill ID:** `shading-and-packaging`  
**Domain:** `maven`  
**Level:** advanced  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** jpackage for deb, msi, dmg.

---

## Purpose

Use when building a distributable JAR or native installer.

---

## Concepts Covered

- **maven-shade-plugin**
- **maven-jpackage-plugin**
- **uber-jar**

---

## Rules / Best Practices

1. Use jpackage for native installers
2. Use shade plugin only for library JARs

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

jpackage for deb, msi, dmg.

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
Implement shading-and-packaging in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
