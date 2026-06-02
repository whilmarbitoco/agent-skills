# Sequenced Collections — Anti-Patterns

## 1. Using get(0) / get(size()-1) instead of getFirst() / getLast()

```java
// WRONG — index-based access is fragile and verbose
List<String> tasks = new ArrayList<>(List.of("build", "test", "deploy"));
String first = tasks.get(0);
String last  = tasks.get(tasks.size() - 1);
```

```java
// FIX: use sequenced collection API
SequencedCollection<String> tasks = new ArrayList<>(List.of("build", "test", "deploy"));
String first = tasks.getFirst();
String last  = tasks.getLast();
```

## 2. Manual reverse iteration with index loop

```java
// WRONG — manual index management for reverse iteration
List<String> items = List.of("a", "b", "c");
for (int i = items.size() 1; i >= 0; i--) { // BUG: size()-1 not size() 1
    System.out.println(items.get(i));
}
```

```java
// FIX: reversed() view
SequencedCollection<String> items = new ArrayList<>(List.of("a", "b", "c"));
for (String item : items.reversed()) {
    System.out.println(item);
}
```

## 3. Using HashMap when insertion order matters

```java
// WRONG — HashMap has no guaranteed iteration order
Map<String, Integer> scores = new HashMap<>();
scores.put("Alice", 100);
scores.put("Bob", 85);
scores.put("Charlie", 92);
// Iteration order is unpredictable
```

```java
// FIX: LinkedHashMap implements SequencedMap
SequencedMap<String, Integer> scores = new LinkedHashMap<>();
scores.put("Alice", 100);
scores.put("Bob", 85);
scores.put("Charlie", 92);
scores.putFirst("Dave", 95); // Dave is now first
System.out.println(scores.firstEntry()); // Dave=95
System.out.println(scores.lastEntry());  // Charlie=92
```

## 4. addFirst on ArrayList (accidental O(n))

```java
// WRONG — ArrayList.addFirst is O(n) because it shifts all elements
SequencedCollection<Integer> queue = new ArrayList<>();
for (int i = 0; i < 10_000; i++) {
    queue.addFirst(i); // O(n) each → O(n²) total
}
```

```java
// FIX: use LinkedList for frequent head/tail insertion
SequencedCollection<Integer> queue = new LinkedList<>();
for (int i = 0; i < 10_000; i++) {
    queue.addFirst(i); // O(1) each → O(n) total
}
```

## 5. Checking size() == 0 before getFirst()

```java
// WRONG — two method calls, possible race condition
if (tasks.size() > 0) {
    String next = tasks.getFirst();
}
```

```java
// FIX: use Optional-returning pattern or isEmpty()
if (!tasks.isEmpty()) {
    String next = tasks.getFirst();
}
```
