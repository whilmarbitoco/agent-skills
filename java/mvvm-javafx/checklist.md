# Checklist

## Implementation
- [ ] Follows core rules from SKILL.md
- [ ] Constructor injection for all dependencies
- [ ] No static mutable state
- [ ] Error handling (no swallowed exceptions)
- [ ] Proper resource cleanup

## Code Review
- [ ] No business logic in wrong layer
- [ ] No FX thread blocked with I/O
- [ ] No raw null returns (use Optional)
- [ ] SLF4J parameterized logging
