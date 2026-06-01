# SKILL-TEMPLATE.md
# Copy this file, fill all sections, and place at:
#   java/<domain>/<skill-id>/SKILL.md

---
name: <skill-id>
description: <One-line description starting with "Use when...">
category: java
tags:
  - java-21
  - <domain>
  - <subdomain>
---

# <Skill Title>

**Skill ID:** `<skill-id>`  
**Domain:** `<domain>` → `<subdomain>`  
**Level:** `<beginner | intermediate | advanced>`  
**Version:** `<semver>`  
**Last Updated:** `<ISO date>`

**Stack:** `<java-21, maven, javafx, kickstartfx, ...>`  
**Requires:** `<skill-id-1>, <skill-id-2>` (or "none")  
**Related:** `<skill-id-3>, <skill-id-4>` (or "none")

**Version Compatibility:** Java `<version>`, Maven `<version>`, JavaFX `<version>`, KickStartFX `<version>`

---

## Purpose

<2-3 sentences explaining what this skill covers and when to use it.>

---

## Concepts Covered

- <Concept 1> — <brief description>
- <Concept 2> — <brief description>
- <Concept 3> — <brief description>

---

## Rules / Best Practices

1. **<Rule name>** — <explanation>
2. **<Rule name>** — <explanation>
3. **<Rule name>** — <explanation>

---

## Code Conventions

| Convention | Rule |
|------------|------|
| Naming | `<rule>` |
| Structure | `<rule>` |
| Typing | `<rule>` |

```java
// ✅ CORRECT
<code example>

// ❌ WRONG — <why>
<code example>
```

---

## Examples

### <Example 1 — Concept Name>

<Context description>

```java
<code>
```

### <Example 2 — Concept Name>

<Context description>

```java
<code>
```

---

## Anti-Patterns

### ❌ <Anti-pattern name>

```java
<code showing the wrong way>
```

**Why:** <explanation>  
**Fix:** <what to do instead>

---

## Checklists

### Setup
- [ ] <Prerequisite step>
- [ ] <Prerequisite step>

### Implementation
- [ ] <Implementation step>
- [ ] <Implementation step>

### Code Review
- [ ] <Review criterion>
- [ ] <Review criterion>

---

## Project-Specific Guidance (Simple POS)

<How this skill applies to the Simple POS System project. Code snippets from the domain.>

<KickStartFX-specific notes if applicable.>

---

## Recommended Reading

### Official (Tier 1)
- [<Source Name>](<URL>) — <what section to read>

### Community (Tier 2)
- [<Source Name>](<URL>) — <what to look for>

---

## Exercises

### Exercise 1 — <Name> (<easy | medium | hard>)

**Task:** <what to build/do>  
**Verification:** <how to confirm it works>

---

## AI/Agent Guide

### Strict Conventions
- <Must-follow rule for AI-generated code>

### Forbidden Patterns
- <Pattern that AI must never produce>

### Preferred Libraries
- `<library>` — <use case>

### Example Prompts

```
<Copy-paste ready prompt for an AI agent>
```

### Architecture Decisions

| Decision | Choice | Rationale |
|----------|--------|-----------|
| `<decision>` | `<choice>` | `<why>` |

### Code Templates

```java
// Template: <name>
// Usage: <when to use this>
<code skeleton>
```
