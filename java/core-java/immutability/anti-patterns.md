# Immutability — Anti-Patterns

## 1. Exposing internal mutable collection from getter

```java
// WRONG — caller can mutate the internal state
class ShoppingCart {
    private final List<String> items = new ArrayList<>();

    public List<String> getItems() {
        return items; // direct reference!
    }
}
// Caller: cart.getItems().add("exploit");
```

```java
// FIX: return unmodifiable view or copy
class ShoppingCart {
    private final List<String> items = new ArrayList<>();

    public List<String> getItems() {
        return List.copyOf(items); // or Collections.unmodifiableList(items)
    }
}
```

## 2. Not copying mutable constructor parameters

```java
// WRONG — caller retains reference to internal list
record Config(List<String> keys) {}
// Caller:
var keys = new ArrayList<>(List.of("a", "b"));
var config = new Config(keys);
keys.add("c"); // mutates the list inside config!
```

```java
// FIX: defensive copy in canonical constructor
record Config(List<String> keys) {
    Config {
        keys = List.copyOf(keys);
    }
}
```

## 3. Using setter-style mutation instead of with-er methods

```java
// WRONG — setters break immutability
class Point {
    private int x;
    private int y;
    public void setX(int x) { this.x = x; } // mutable!
}
```

```java
// FIX: with-er returns a new instance
record Point(int x, int y) {
    public Point withX(int newX) { return new Point(newX, this.y); }
    public Point withY(int newY) { return new Point(this.x, newY); }
}
```

## 4. Date/Time fields not defensively copied

```java
// WRONG — java.time is immutable but java.util.Date is not
class Event {
    private final Date timestamp;
    public Event(Date timestamp) { this.timestamp = timestamp; } // not copied!
    public Date getTimestamp() { return timestamp; } // exposed!
}
```

```java
// FIX: copy mutable date types
class Event {
    private final Date timestamp;
    public Event(Date timestamp) { this.timestamp = new Date(timestamp.getTime()); }
    public Date getTimestamp() { return new Date(timestamp.getTime()); }
}
```

## 5. Using a class when a record suffices

```java
// WRONG — 25 lines of boilerplate for a value object
public final class Money {
    private final java.math.BigDecimal amount;
    private final java.util.Currency currency;
    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }
    public BigDecimal getAmount() { return amount; }
    public Currency getCurrency() { return currency; }
    @Override public boolean equals(Object o) { /* ... 10 lines ... */ }
    @Override public int hashCode() { /* ... */ }
    @Override public String toString() { /* ... */ }
}
```

```java
// FIX: one line with record, manual equals/hashCode not needed
import java.math.BigDecimal;
import java.util.Currency;

record Money(BigDecimal amount, Currency currency) {}
```
