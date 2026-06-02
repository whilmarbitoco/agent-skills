# Records & Sealed — Quick Reference

| Feature | Syntax |
|---|---|
| Record | `record Point(int x, int y) {}` |
| Compact constructor | `Point { if (x < 0) throw ...; }` |
| Sealed interface | `sealed interface Shape permits Circle, Rect {}` |
| Record pattern | `case Circle(var x, var y, var r) -> ...` |
| Exhaustive switch | `switch (shape) { case Circle c -> ...; case Rect r -> ...; }` |
| Defensive copy | `this.list = List.copyOf(list);` in compact ctor |

| DO | DON'T |
|---|---|
| Records for value objects | Hand-write getters/equals/hashCode |
| Sealed for fixed hierarchies | Open interfaces for closed ADTs |
| Exhaustive switch, no default | Add default to sealed switch |
