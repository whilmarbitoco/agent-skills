---
name: offline-sync-readiness
description: "Use when preparing the POS for online synchronization."
category: java
tags:
  - java-21
  - pos-domain
---

# Offline Sync Readiness

**Skill ID:** `offline-sync-readiness`  
**Domain:** `pos-domain`  
**Level:** advanced  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Currently offline-first. Future: upload sales.

---

## Purpose

Use when preparing the POS for online synchronization.

---

## Concepts Covered

- **Local-first architecture**
- **Conflict resolution**
- **Sync queue**

---

## Rules / Best Practices

1. All operations work offline
2. Queue sync when offline

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

Currently offline-first. Future: upload sales.

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
Implement offline-sync-readiness in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
