---
name: receipt-generation
description: "Use when generating receipts with JasperReports."
category: java
tags:
  - java-21
  - pos-domain
---

# Receipt Generation

**Skill ID:** `receipt-generation`  
**Domain:** `pos-domain`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Receipt: header, body, footer with barcode.

---

## Purpose

Use when generating receipts with JasperReports.

---

## Concepts Covered

- **JasperReports**
- **Thermal printer**
- **Receipt template**

---

## Rules / Best Practices

1. Use jrxml templates
2. Support raw ESC/POS printing

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

Receipt: header, body, footer with barcode.

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
Implement receipt-generation in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
