---
name: tableview-best-practices
description: "Use when working with JavaFX TableView and TableColumn."
category: java
tags:
  - java-21
  - ui-javafx
---

# TableView Best Practices

**Skill ID:** `tableview-best-practices`  
**Domain:** `ui-javafx`  
**Level:** intermediate  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Product table with Money cell. Sale history with date formatting.

---

## Purpose

Use when working with JavaFX TableView and TableColumn.

---

## Concepts Covered

- **TableColumn**
- **CellValueFactory**
- **CellFactory**
- **SortedList**
- **FilteredList**

---

## Rules / Best Practices

1. Use PropertyValueFactory for simple cases
2. Custom CellFactory for formatted cells
3. Use FilteredList for search

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

Product table with Money cell. Sale history with date formatting.

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
Implement tableview-best-practices in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
