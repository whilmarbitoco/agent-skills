---
name: native-installers
description: "Use when building platform-specific native installers."
category: java
tags:
  - java-21
  - packaging
---

# Native Installers

**Skill ID:** `native-installers`  
**Domain:** `packaging`  
**Level:** advanced  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** deb for Linux, msi for Windows, dmg for macOS.

---

## Purpose

Use when building platform-specific native installers.

---

## Concepts Covered

- **deb**
- **msi**
- **dmg**
- **WiX**

---

## Rules / Best Practices

1. deb on Linux, msi on Windows (WiX), dmg on macOS

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

deb for Linux, msi for Windows, dmg for macOS.

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
Implement native-installers in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
