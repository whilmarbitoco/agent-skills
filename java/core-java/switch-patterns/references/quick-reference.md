# Switch Patterns — Quick Reference

| Pattern | Syntax |
|---|---|
| Type pattern | `case String s -> ...` |
| Guarded pattern | `case String s when s.isEmpty() -> ...` |
| Null case | `case null -> ...` |
| Record destructuring | `case Point(int x, int y) -> ...` |
| Default | `default -> ...` |

| Rule | Detail |
|---|---|
| Dominance | More specific after less specific |
| Null | Must be explicit (`case null`) in Java 21 |
| Exhaustion | Sealed types: no default needed |
| Guard binding | `when` can reference pattern variable |

| DO | DON'T |
|---|---|
| `case null` for null safety | Let null hit `default` accidentally |
| `when` guards for conditions | Nested if inside case body |
| Type patterns over instanceof chains | Manual cast after instanceof |
