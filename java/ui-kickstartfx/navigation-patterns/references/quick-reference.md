# Navigation Patterns — Quick Reference

| Concept | Convention |
|---|---|
| Route type | `sealed interface Route permits A, B, C` |
| Navigation service | Constructor-injected, not static singleton |
| View loading | Lazy via `Supplier<Node>`, not all at startup |
| History | `Deque<NavState>` capped at 20 |
| Context passing | `NavigationContext(Map<String, Object>)` |
| Back navigation | `history.pop()` restores previous `NavState` |
