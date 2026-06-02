# Immutability — Quick Reference

| Pattern | Code |
|---|---|
| Record | `record Point(int x, int y) {}` |
| Defensive copy (ctor) | `this.list = List.copyOf(list);` |
| Defensive access (getter) | `return List.copyOf(items);` |
| Unmodifiable view | `Collections.unmodifiableList(items);` |
| With-er | `public Point withX(int x) { return new Point(x, this.y); }` |
| Immutable inline | `List.of("a", "b")`, `Map.of("k", 1)` |

| Use | When |
|---|---|
| Record | Pure data carrier, no custom behavior needed |
| Class | Entity with identity, lifecycle, or complex behavior |
| Defensive copy | Accepting mutable input or returning mutable state |
| With-er | "Modify" an immutable object (returns new instance) |
