# Switch Patterns — Checklist

## Implementation

- [ ] Replace instanceof-if chains with switch pattern matching
- [ ] Handle `null` with explicit `case null` branch
- [ ] Use `when` guards instead of nested if statements in case bodies
- [ ] Order cases from most specific to least specific (dominance)
- [ ] Use exhaustive switch on sealed types; use `default` for open types

## Review

- [ ] No `instanceof` + cast in new code
- [ ] No NullPointerException risk from unhandled null in switch
- [ ] All `when` guards extracted to case level (not nested if)
- [ ] Compiler warnings checked for dominated/duplicate cases
