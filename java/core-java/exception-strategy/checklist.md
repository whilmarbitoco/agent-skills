# Exception Strategy — Checklist

## Implementation

- [ ] Return `Optional<T>` when "absent" is a normal case
- [ ] Define domain exceptions for business rule violations
- [ ] Catch specific exception types, never `Exception` or `Throwable`
- [ ] Use SLF4J parameterized logging (`log.info("{}", val)`)
- [ ] Log at appropriate level: `error` for system failures, `warn` for recoverable
- [ ] Never use empty catch blocks

## Review

- [ ] No `return null` on methods that may not find a value
- [ ] No `catch (Exception e)` in new code
- [ ] No string concatenation in log statements
- [ ] Domain exceptions carry context (IDs, parameters)
- [ ] No exceptions used for normal control flow
