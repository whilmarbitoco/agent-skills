---
name: javafx-maven-plugin
description: "Use when configuring the Gluon JavaFX Maven plugin."
category: java
tags:
  - java-21
  - maven
---

# JavaFX Maven Plugin (Gluon)

**Skill ID:** `javafx-maven-plugin`  
**Domain:** `maven`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** javafx:run in dev. Module path in pom.xml.

---

## Purpose

Use when configuring the Gluon JavaFX Maven plugin.

---

## Concepts Covered

- **javafx:run**
- **javafx:jlink**
- **javafx:native**

---

## Rules / Best Practices

1. Configure mainClass in plugin
2. Set release=21 in compiler plugin

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

javafx:run in dev. Module path in pom.xml.

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
Implement javafx-maven-plugin in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
