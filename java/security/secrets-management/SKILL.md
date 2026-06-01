---
name: secrets-management
description: "Use when handling API keys or credentials in a desktop app."
category: java
tags:
  - java-21
  - security
---

# Secrets Management

**Skill ID:** `secrets-management`  
**Domain:** `security`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** .env for dev. OS keyring for production.

---

## Purpose

Use when handling API keys or credentials in a desktop app.

---

## Concepts Covered

- **dotenv**
- **Environment variables**
- **Keyring**

---

## Rules / Best Practices

1. Use .env for dev
2. Use OS keyring for production
3. Never commit .env

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

.env for dev. OS keyring for production.

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
Implement secrets-management in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
