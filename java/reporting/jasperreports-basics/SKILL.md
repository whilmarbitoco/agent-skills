---
name: jasperreports-basics
description: "Use when creating JasperReports templates and generating reports."
category: java
tags:
  - java-21
  - reporting
---

# JasperReports Basics

**Skill ID:** `jasperreports-basics`  
**Domain:** `reporting`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Reports: daily sales, inventory, cash session.

---

## Purpose

Use when creating JasperReports templates and generating reports.

---

## Concepts Covered

- **JRXML**
- **JasperCompileManager**
- **JRBeanCollectionDataSource**
- **Subreports**

---

## Rules / Best Practices

1. Design jrxml templates
2. Compile on first load, cache compiled

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

Reports: daily sales, inventory, cash session.

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
Implement jasperreports-basics in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
