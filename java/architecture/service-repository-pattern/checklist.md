# Service + Repository Pattern — Checklist

## Implementation
- [ ] Repository interface + implementation are separate
- [ ] Repository methods return `Optional` or collections — never null
- [ ] Service is the transaction boundary (single transaction per use case method)
- [ ] Service uses constructor injection for repositories
- [ ] Input to service methods uses request records, not long parameter lists
- [ ] SLF4J parameterized logging (`log.info("{}", val)`)

## Review
- [ ] No business logic in repository implementations
- [ ] Repository does not call other repositories
- [ ] No string concatenation in any log statement
- [ ] All exceptions are domain-specific, not raw SQL exceptions leaked outward
