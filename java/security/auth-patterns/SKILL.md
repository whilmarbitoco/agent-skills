---
name: auth-patterns
description: "Use when implementing local user authentication."
category: java
tags:
  - java-21
  - security
---

# Authentication Patterns (Local)

**Skill ID:** `auth-patterns`  
**Domain:** `security`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Cashier (sales), admin (full). BCrypt hashing.

---

## Purpose

Use when implementing local user authentication.

---

## Concepts Covered

- **Password hashing**
- **Session management**
- **RBAC**
- **BCrypt**

---

## Rules / Best Practices

1. Use BCrypt for hashing
2. Session token with expiry
3. RBAC for cashier/admin

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

Cashier (sales), admin (full). BCrypt hashing.

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
Implement auth-patterns in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
