# Records & Sealed Interfaces — Checklist

## Implementation

- [ ] Use `record` for all value objects and DTOs
- [ ] Use `sealed interface` when implementations are a fixed, known set
- [ ] Write exhaustive `switch` expressions on sealed types (no `default`)
- [ ] Use record patterns (`case Circle(var x, var y, var r)`) to destructure
- [ ] Defensively copy mutable components in record canonical constructors

## Review

- [ ] No hand-written equals/hashCode/toString on data classes
- [ ] No `default` branch on sealed type switches
- [ ] No mutable collections exposed from records
- [ ] `permits` clause matches actual implementing classes
