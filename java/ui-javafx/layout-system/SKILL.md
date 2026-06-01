---
name: layout-system
description: "Use when working with Pane, VBox, HBox, GridPane, BorderPane."
category: java
tags:
  - java-21
  - ui-javafx
---

# JavaFX Layout System

**Skill ID:** `layout-system`  
**Domain:** `ui-javafx`  
**Level:** beginner  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**POS Guidance:** Main shell: BorderPane. Forms: GridPane. Cards: VBox.

---

## Purpose

Use when working with Pane, VBox, HBox, GridPane, BorderPane.

---

## Concepts Covered

- **BorderPane**
- **VBox-HBox**
- **GridPane**
- **StackPane**

---

## Rules / Best Practices

1. Use BorderPane for top-level shell
2. Use GridPane for forms
3. Avoid AnchorPane

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

Main shell: BorderPane. Forms: GridPane. Cards: VBox.

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
Implement layout-system in the Simple POS following the rules above.
Use Java 21 features where applicable.
```

### Code Templates

See canonical-stack.yaml for dependencies.
