# SKILL-TEMPLATE.md
# Standard template for Java 21 LTS agent skills.
# Follows the Agent Skills open standard (agentskills.io) for cross-agent compatibility.
# Copy to: java/<domain>/<skill-id>/SKILL.md

---
# REQUIRED — name of the skill (directory name must match)
name: <skill-id>

# REQUIRED — what the skill does and when it loads (max 1024 chars)
# Start with action verb. Include trigger keywords for auto-detection.
# Good: "Extends agent's knowledge of Java 21 virtual threads. Use when writing
#        concurrent code, optimizing I/O-bound workloads, or debugging thread pinning."
description: >
  <What this skill teaches the agent.>

# OPTIONAL — license for this skill
license: Apache-2.0

# OPTIONAL — environment requirements (max 500 chars)
# Only include if the skill has specific requirements.
compatibility: Java 21+, Maven 3.9+

# OPTIONAL — arbitrary metadata map
metadata:
  domain: <domain-name>
  level: <beginner | intermediate | advanced>
  stack:
    - java-21
    - <other-tech>
  requires:
    - <prerequisite-skill-id>
  version: "1.0.0"

# OPTIONAL — pre-approved tools (experimental, agent-dependent)
allowed-tools: Bash(*) Read Write
---

# <Skill Title>

<One-paragraph summary. Keep under 5 lines. Everything after this should be
structured for progressive disclosure — concise here, detailed in references/.>

## When This Skill Applies

<Bullet list of concrete triggers. Helps both agents and humans decide when to load this.>
- <Trigger 1>
- <Trigger 2>
- <Trigger 3>

## Core Rules

<Actionable rules. Each starts with an imperative verb. Keep under 15 rules.>

1. **Rule** — brief clarification
2. **Rule** — brief clarification

## Examples

### Correct

```java
// The right approach
<code>
```

### Incorrect

```java
// The wrong approach
<code>
```

**Why:** <One-line explanation.>

## Conventions

| Aspect | Convention |
|--------|-----------|
| naming | `<rule>` |
| typing | `<rule>` |
| structure | `<rule>` |

## Anti-Patterns

- **<Anti-pattern>** — what to avoid and why

## Verification

### Implementation checklist
- [ ] <Check>
- [ ] <Check>

### Code review checklist
- [ ] <Check>
- [ ] <Check>

## Reference Material

Detailed documentation that agents load on demand. Keep SKILL.md body under
200 lines; move lengthy content here.

- `references/<skill-id>.md` — full technical reference
- `scripts/<name>.sh` — validation or automation scripts
- `assets/<name>` — templates or static resources

## Recommended Reading

- [<Source>](<URL>) — <what to look for>
