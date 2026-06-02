# Switch Pattern Matching — Anti-Patterns

## 1. instanceof-if chain instead of pattern matching

```java
// WRONG — verbose, error-prone cast
static String describe(Object value) {
    if (value instanceof String) {
        String s = (String) value;
        return "String of length " + s.length();
    } else if (value instanceof Integer) {
        Integer i = (Integer) value;
        return "Integer: " + i;
    } else if (value instanceof Double) {
        Double d = (Double) value;
        return "Double: " + d;
    } else {
        return "Unknown";
    }
}
```

```java
// FIX: pattern matching in switch
static String describe(Object value) {
    return switch (value) {
        case String s  -> "String of length " + s.length();
        case Integer i -> "Integer: " + i;
        case Double d  -> "Double: " + d;
        case null      -> "null";
        default        -> "Unknown";
    };
}
```

## 2. Not handling null in switch (pre-Java 21 behavior)

```java
// WRONG — throws NullPointerException when value is null
static int classify(Object value) {
    return switch (value) {
        case String s  -> s.length();
        case Integer i -> i;
        default        -> 0;
    };
}
```

```java
// FIX: explicit null case (Java 21+)
static int classify(Object value) {
    return switch (value) {
        case null      -> -1;
        case String s  -> s.length();
        case Integer i -> i;
        default        -> 0;
    };
}
```

## 3. Guard in an if statement inside the case

```java
// WRONG — defeats the purpose of guard clauses
static String label(Object value) {
    return switch (value) {
        case String s -> {
            if (s.isEmpty()) yield "empty";
            if (s.length() > 10) yield "long";
            yield "short";
        }
        default -> "other";
    };
}
```

```java
// FIX: use `when` guard directly on the case
static String label(Object value) {
    return switch (value) {
        case String s when s.isEmpty()      -> "empty";
        case String s when s.length() > 10  -> "long";
        case String s                       -> "short";
        case null                           -> "null";
        default                             -> "other";
    };
}
```

## 4. Dominance violation — unreachable case

```java
// WRONG — second case is dominated by the first (Object matches everything)
static int priority(Object value) {
    return switch (value) {
        case Object o -> 0;      // dominates everything below
        case String s -> 1;      // ERROR: unreachable
        default       -> 2;
    };
}
```

```java
// FIX: order from most specific to least specific
static int priority(Object value) {
    return switch (value) {
        case String s -> 1;
        case Integer i -> 2;
        case null     -> -1;
        default       -> 0;
    };
}
```

## 5. Casting after instanceof without pattern variable

```java
// WRONG — old-style cast after instanceof check
static double toDouble(Object value) {
    if (value instanceof Number) {
        return ((Number) value).doubleValue(); // unnecessary cast
    }
    return 0.0;
}
```

```java
// FIX: pattern variable is already typed
static double toDouble(Object value) {
    return switch (value) {
        case Number n -> n.doubleValue();
        case null     -> 0.0;
        default       -> 0.0;
    };
}
```
