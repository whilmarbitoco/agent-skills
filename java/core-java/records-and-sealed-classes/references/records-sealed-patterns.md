# Records, Sealed Classes & Pattern Matching — Full Reference

## JEP Index

| JEP | Title | Status |
|-----|-------|--------|
| 395 | Record Classes | Final (Java 16) |
| 409 | Sealed Classes | Final (Java 17) |
| 440 | Record Patterns | Final (Java 21) |
| 441 | Pattern Matching for switch | Final (Java 21) |
| 443 | Unnamed Patterns & Variables | Final (Java 21) |

## Record Definition

```java
// Compact syntax — auto-generates: constructor, getters, equals, hashCode, toString
public record Money(BigDecimal amount, Currency currency) {}

// Compact constructor for validation
public record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(amount);
        if (amount.signum() < 0) throw new IllegalArgumentException();
    }
}

// Additional methods
public record Money(BigDecimal amount, Currency currency) {
    public Money add(Money other) {
        return new Money(this.amount.add(other.amount), this.currency);
    }
}
```

## Sealed Class Hierarchy

```java
// Sealed interface with record variants
public sealed interface Shape permits Circle, Rectangle, Triangle {
    double area();

    record Circle(double radius) implements Shape {
        public double area() { return Math.PI * radius * radius; }
    }
    record Rectangle(double width, double height) implements Shape {
        public double area() { return width * height; }
    }
    record Triangle(double base, double height) implements Shape {
        public double area() { return 0.5 * base * height; }
    }
}
```

## Exhaustive Switch

```java
// No default needed — compiler checks all permitted types
double area = switch (shape) {
    case Circle c    -> c.area();
    case Rectangle r -> r.area();
    case Triangle t  -> t.area();
};
```

## Record Patterns (Java 21)

```java
// Destructure records in pattern matching
if (shape instanceof Circle(var r)) {
    System.out.println("Radius: " + r);
}

// Nested destruction
if (sale instanceof Sale(var id, var lines)) {
    // lines is List<SaleLine>
}
```

## Unnamed Patterns (Java 21, JEP 443)

```java
// Use _ for patterns you don't need
if (shape instanceof Circle _) {
    System.out.println("It's a circle");
}

// Unnamed variables
int total = shapes.stream()
    .mapToInt(_ -> 1)  // don't need the shape object
    .sum();
```

## Common Pitfalls

1. **Records aren't for entities** — they're for immutable value objects. Ebean entities should remain classes.
2. **Compact constructor doesn't assign fields** — the implicit constructor body does. You only validate.
3. **Sealed interfaces need `permits`** — every direct subtype must be listed.
