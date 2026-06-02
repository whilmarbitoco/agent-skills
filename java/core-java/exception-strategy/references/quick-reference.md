# Exception Strategy — Quick Reference

| Level | When to use |
|---|---|
| `error` | System failure, data loss, requires intervention |
| `warn` | Recoverable issue, degraded behavior, unexpected but handled |
| `info` | Notable lifecycle events (started, stopped, request completed) |
| `debug` | Diagnostic details, variable dumps, flow tracing |

| Pattern | Code |
|---|---|
| Optional return | `Optional<User> findById(String id)` |
| Domain exception | `class OrderNotFoundException extends RuntimeException { ... }` |
| Parameterized log | `log.info("Saved order {}", orderId);` |
| Wrap & rethrow | `throw new DomainException("context", cause);` |
| Guard clause | `Objects.requireNonNull(input, "input must not be null");` |

| DO | DON'T |
|---|---|
| Optional for "may be absent" | Return null |
| Specific catch blocks | catch(Exception e) |
| Parameterized logging | String concat in log args |
| Domain exceptions for business errors | Generic RuntimeException with message |
