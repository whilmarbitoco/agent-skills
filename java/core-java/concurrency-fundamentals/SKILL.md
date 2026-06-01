---
name: concurrency-fundamentals
description: "Use when working with ExecutorService, CompletableFuture, locks, concurrent collections."
category: java
tags:
  - java-21
  - core-java
---

# Concurrency Fundamentals

**Skill ID:** `concurrency-fundamentals`  
**Domain:** `core-java`  
**Level:** advanced  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** ConcurrentHashMap for product cache. CompletableFuture for async composition.

---

## Purpose

Use when working with ExecutorService, CompletableFuture, locks, concurrent collections.

---

## Concepts Covered

- **ExecutorService**
- **CompletableFuture**
- **ReentrantLock**
- **ConcurrentHashMap**

---

## Rules / Best Practices

1. Always shutdown ExecutorService
2. Prefer CompletableFuture over raw Future
3. Use ConcurrentHashMap for shared maps

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

ConcurrentHashMap for product cache. CompletableFuture for async composition.

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
Implement concurrency-fundamentals in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
