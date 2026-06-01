---
name: barcode-generation
description: "Use when generating barcodes or QR codes."
category: java
tags:
  - java-21
  - reporting
---

# Barcode Generation with ZXing

**Skill ID:** `barcode-generation`  
**Domain:** `reporting`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** QR on receipt. Code128 on product labels.

---

## Purpose

Use when generating barcodes or QR codes.

---

## Concepts Covered

- **QR Code**
- **Code128**
- **EAN-13**
- **BufferedImage**

---

## Rules / Best Practices

1. Use ZXing library
2. QR for receipt lookup
3. Code128 for product IDs

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

QR on receipt. Code128 on product labels.

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
Implement barcode-generation in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
