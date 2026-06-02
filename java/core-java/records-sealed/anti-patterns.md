# Records & Sealed Interfaces — Anti-Patterns

## 1. Writing boilerplate DTO classes instead of records

```java
// WRONG — 30 lines of boilerplate for what a record does in 1
public final class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) { this.x = x; this.y = y; }
    public int x() { return x; }
    public int y() { return y; }

    @Override public boolean equals(Object o) { /* ... */ }
    @Override public int hashCode() { /* ... */ }
    @Override public String toString() { /* ... */ }
}
```

```java
// FIX: one line
record Point(int x, int y) {}
```

## 2. Open interface when the type hierarchy is fixed

```java
// WRONG — any class can implement Shape; switch can never be exhaustive
interface Shape {}
record Circle(double radius) implements Shape {}
record Rectangle(double w, double h) implements Shape {}
// Later: someone adds Triangle without the switch being updated
```

```java
// FIX: sealed interface — compiler knows all permitted subtypes
sealed interface Shape permits Circle, Rectangle {}
record Circle(double radius) implements Shape {}
record Rectangle(double w, double h) implements Shape {}

// Exhaustive switch — no default needed, compiler verifies completeness
double area(Shape shape) {
    return switch (shape) {
        case Circle c    -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.w() * r.h();
    };
}
```

## 3. Using instanceof chains instead of pattern matching

```java
// WRONG — verbose instanceof + cast
double area(Shape shape) {
    if (shape instanceof Circle) {
        Circle c = (Circle) shape;
        return Math.PI * c.radius() * c.radius();
    } else if (shape instanceof Rectangle) {
        Rectangle r = (Rectangle) shape;
        return r.w() * r.h();
    }
    throw new IllegalArgumentException("Unknown shape");
}
```

```java
// FIX: pattern matching in switch
double area(Shape shape) {
    return switch (shape) {
        case Circle c    -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.w() * r.h();
    };
}
```

## 4. Adding default to exhaustive sealed switch

```java
// WRONG — default hides missing cases when new type is added
double area(Shape shape) {
    return switch (shape) {
        case Circle c    -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.w() * r.h();
        default          -> 0; // silently swallows new types
    };
}
```

```java
// FIX: remove default — compiler will error if a new permitted type is added
double area(Shape shape) {
    return switch (shape) {
        case Circle c    -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.w() * r.h();
    };
}
```

## 5. Mutable records

```java
// WRONG — records are immutable by contract; using a mutable field breaks it
record Team(List<String> members) {
    // Caller can mutate the internal list!
}
```

```java
// FIX: use List.of() or copy in canonical constructor
record Team(List<String> members) {
    Team {
        members = List.copyOf(members); // defensive copy
    }
}
```
