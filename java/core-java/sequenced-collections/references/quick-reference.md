# Sequenced Collections — Quick Reference

| Method | Returns | Complexity (LinkedList) | Complexity (ArrayList) |
|---|---|---|---|
| `getFirst()` | first element | O(1) | O(1) |
| `getLast()` | last element | O(1) | O(1) |
| `addFirst(e)` | void | O(1) | O(n) |
| `addLast(e)` | void | O(1) | O(1)* |
| `removeFirst()` | removed element | O(1) | O(n) |
| `removeLast()` | removed element | O(1) | O(1) |
| `reversed()` | reverse view | O(1) | O(1) |

| Map method | Returns |
|---|---|
| `putFirst(k,v)` | previous value or null |
| `putLast(k,v)` | previous value or null |
| `pollFirstEntry()` | removed entry or null |
| `pollLastEntry()` | removed entry or null |
| `sequencedKeySet()` | `SequencedSet<K>` |
| `reversed()` | reverse view |

| DO | DON'T |
|---|---|
| `LinkedList` for head ops | `ArrayList.addFirst` in loops |
| `LinkedHashMap` for order | `HashMap` when order matters |
