# Navigation Patterns — Checklist

## Implementation

- [ ] Routes defined as sealed interface with exhaustive cases
- [ ] Navigation service injected via constructor, never accessed as static singleton
- [ ] Views lazy-loaded via Supplier, not all at startup
- [ ] History stack capped at 20 entries with FIFO eviction
- [ ] Context passed via NavigationContext map, never shared mutable state

## Review

- [ ] No controller directly instantiates another controller
- [ ] No `default` branch on sealed route switches
- [ ] Navigation service interface is mockable for unit tests
- [ ] Back navigation works correctly for 3+ levels deep
