# Sqlite Desktop Anti-Patterns

See SKILL.md for core rules.

## Common Mistakes

- **Direct instantiation** in UI layer — use constructor injection
- **Swallowed exceptions** — always log or propagate
- **Null returns** — use Optional instead of null
- **String concat in logs** — use SLF4J parameterized messages
