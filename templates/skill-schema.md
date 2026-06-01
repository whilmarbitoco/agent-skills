# Skill Schema — Java 21 LTS Agent Skills
# Aligns with the Agent Skills open standard (agentskills.io).
# This is a reference document, not a YAML file.

## Required frontmatter fields (per agentskills.io spec)

- **name** — Max 64 chars. Lowercase a-z, 0-9, hyphens only.
  Must match parent directory name. No consecutive hyphens.
- **description** — Max 1024 chars, non-empty.
  Starts with action verb. Should answer: what does this do? when should it load?
  Include trigger keywords for agent auto-detection.

## Optional frontmatter fields (per agentskills.io spec)

- **license** — License name or reference to bundled license file. e.g. "Apache-2.0"
- **compatibility** — Max 500 chars. Only if specific requirements.
  e.g. "Java 21+, Maven 3.9+" or "Requires git, docker, jq, network access"
- **allowed-tools** — Space-separated pre-approved tools (experimental).
  e.g. "Bash(git:*) Read Write"
- **metadata** — Arbitrary string->string map for skill-specific data:
  - domain: One of the 13 canonical domains
  - subdomain: Granular module within the domain
  - level: beginner | intermediate | advanced
  - stack: ["java-21", "maven", "javafx", "kickstartfx"]
  - requires: [prerequisite skill IDs]
  - related: [related skill IDs]
  - version: Semver string
  - last_updated: ISO 8601 date

## Body structure (Markdown after frontmatter)

Recommended sections in order:

1. **Title** — H1 with skill name
2. **Summary** — One paragraph, under 5 lines
3. **When This Skill Applies** — Bulleted trigger scenarios
4. **Core Rules** — Numbered, actionable rules (max 15)
5. **Examples** — Java code: correct vs incorrect with "Why" explanation
6. **Conventions** — Table of naming/typing/structure rules
7. **Anti-Patterns** — Bulleted list with explanation
8. **Verification** — Two checklists (implementation + review)
9. **Reference Material** — Links to references/, scripts/, assets/
10. **Recommended Reading** — Tier 1 (official) + Tier 2 (community) URLs

## Directory structure

```
skill-name/
  SKILL.md                 Required: frontmatter + concise body (<200 lines)
  references/              Optional: detailed docs loaded on demand
    reference.md
  scripts/                 Optional: executable code
    validate.sh
  assets/                  Optional: templates, images, data
  CHANGELOG.md             Optional: version history
```

## Size targets (agentskills.io guidance)

- name: 1-64 chars
- description: 1-1024 chars
- compatibility: 1-500 chars
- SKILL.md body: under 200 lines (~5000 tokens max)
- Individual files: 8-14k chars (peer skill range)
- Full SKILL.md: under 100k chars

## Cross-agent compatibility rules

1. Write instructions as if talking to a competent developer, not driving a specific tool.
2. Avoid Hermes/Claude Code/Cursor-specific features in the core body.
3. Use plain file references (file paths), not tool-specific commands.
4. Keep body concise; heavy content in referenced files for progressive disclosure.
5. Every skill must work as standalone documentation for a human reader.
