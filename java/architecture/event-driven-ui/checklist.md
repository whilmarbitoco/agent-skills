# Event-Driven UI — Checklist

## Implementation
- [ ] Events are `record` types — immutable by design
- [ ] Bus dispatches on FX Application Thread for UI-bound subscribers
- [ ] Weak references for subscribers to prevent memory leaks
- [ ] Handlers unregistered on view disposal (`close()`, `dispose()`)
- [ ] One event bus per module — not a single god-bus
- [ ] Events describe facts ("what happened"), never commands ("do this")

## Review
- [ ] No mutable event classes with setters
- [ ] No events used for direct parent/child communication (use binding instead)
- [ ] No event carries command semantics or receiver-specific formatting
- [ ] Subscription cleanup verified for every controller that registers
