# Sequenced Collections — Checklist

## Implementation

- [ ] Use `getFirst()` / `getLast()` instead of index-based access
- [ ] Use `reversed()` instead of manual reverse-index iteration
- [ ] Use `LinkedHashMap` / `LinkedHashSet` when insertion order matters
- [ ] Use `LinkedList` for frequent `addFirst` / `removeFirst` operations
- [ ] Accept `SequencedCollection` / `SequencedMap` in method parameters when order is needed

## Review

- [ ] No `get(0)` or `get(size()-1)` on List/Map in new code
- [ ] No manual `for (int i = size-1 ...)` reverse loops
- [ ] `HashMap` / `HashSet` not used where order matters
- [ ] `ArrayList.addFirst` / `removeFirst` call sites checked for performance
